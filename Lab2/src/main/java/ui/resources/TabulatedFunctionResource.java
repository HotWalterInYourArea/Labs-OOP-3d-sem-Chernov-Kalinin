package ui.resources;
import functions.CompositeFunction;
import functions.MathFunction;
import functions.TabulatedFunction;
import functions.factory.TabulatedFunctionFactory;
import io.dropwizard.jersey.sessions.Session;
import jakarta.inject.Singleton;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ui.dto.ArrayTabulatedRequestDTO;
import ui.dto.CreateComplexFunctionDTO;
import ui.dto.FunctionTabulatedFunctionRequestDTO;
import ui.dto.SessionStorageDTO;
import ui.service.FunctionSerializer;
import ui.service.SimpleFunctionsService;
import ui.service.TabulatedFunctionFactoryCookieHandler;
import java.util.*;

@Path("tabulatedFunctions")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class TabulatedFunctionResource {
    private final TabulatedFunctionFactoryCookieHandler cookieHandler=new TabulatedFunctionFactoryCookieHandler();
    private Map<String, byte[]> allFunctions= SimpleFunctionsService.putSimpleFunctions();
    @GET
    @Path("/getFunctionNames")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getFunctionNames(@Context HttpServletRequest request, @Session HttpSession session) {
        System.out.println(allFunctions.get("Нулевая функция"));
        System.out.println(request.getSession().getAttribute("functions"));
        System.out.println(session.getCreationTime());
        for (String key : allFunctions.keySet()) {
            session.setAttribute(key,allFunctions.get(key));
        }
        return new ArrayList<>(allFunctions.keySet());
    }
    @POST
    @Path("/getFunctions")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getFunctions(@Context HttpServletRequest request, @Session HttpSession session,@Valid SessionStorageDTO sessionStorage) {
        //System.out.println(sessionStorage.getSessionMap().getBytes());
        //System.out.println(FunctionSerializer.deserializeFunction(sessionStorage.getSessionMap()));
        ArrayList<String> encodedBytes=new ArrayList<>();
        for (String key : allFunctions.keySet()) {
            encodedBytes.add(Base64.getEncoder().encodeToString(allFunctions.get(key)));
        }
        return encodedBytes;
    }
    @POST
    @Path("/create/{functionName}")
    public List<String> createFunction(@PathParam("functionName") String functionName,
                                       @Context HttpServletRequest request, @Session HttpSession session, @Valid CreateComplexFunctionDTO sessionStorage) {
        String[] functionnames=sessionStorage.getSessionFunctionNames().split(",");
        String[] bytenames=sessionStorage.getSessionByteFunctions().split(",");
        HashMap<String,MathFunction> functionDict=new HashMap<>();
        for (int i = 0; i < functionnames.length; i++){
            System.out.println(bytenames[i]);
            functionDict.put(functionnames[i],FunctionSerializer.deserializeFunction(Base64.getDecoder().decode(bytenames[i])));
        }
        if (functionDict.containsKey(functionName)) {
            throw new IllegalArgumentException("Введенное имя функции уже существует");
        }
        String[] functions=sessionStorage.getFunctionsToApply().split(",");
        List<MathFunction> functionList = Arrays.stream(functions).map(functionDict::get).toList().reversed();
        CompositeFunction function = CompositeFunction.createCompositeFunctionFromList(functionList);
        byte[] serializedFunction = FunctionSerializer.serializeCustomFunction(function);
        ArrayList<String> resultList=new ArrayList<>(Arrays.asList(bytenames));
        resultList.add(Base64.getEncoder().encodeToString(serializedFunction));
        return resultList;
    }
    @POST
    @Path("/createTabulatedFunctionWithTableByte")
    public Response createTabulatedFunctionWithTableByte(@Valid ArrayTabulatedRequestDTO tabulatedFunctionRequest,
                                                         @Context HttpServletRequest request,
                                                         @Context HttpServletResponse response) {
        TabulatedFunctionFactory factory = cookieHandler.determineFabric(request, response);
        System.out.println(request.getSession().getCreationTime());
        TabulatedFunction function = factory.create(
                tabulatedFunctionRequest.getXValues(),
                tabulatedFunctionRequest.getYValues()
        );

        return Response.ok(FunctionSerializer.serializeByte(function)).build();
    }
    @POST
    @Path("/createTabulatedFunctionWithTableJSON")
    public Response createTabulatedFunctionWithTableJSON(@Valid ArrayTabulatedRequestDTO tabulatedFunctionRequest,
                                                       @Context HttpServletRequest request,
                                                       @Context HttpServletResponse response) {
        TabulatedFunctionFactory factory = cookieHandler.determineFabric(request, response);
        TabulatedFunction function = factory.create(
                tabulatedFunctionRequest.getXValues(),
                tabulatedFunctionRequest.getYValues()
        );
        return Response.ok(FunctionSerializer.serializeJson(function)).build();
    }

    @POST
    @Path("/createTabulatedFunctionWithTableXML")
    public Response createTabulatedFunctionWithTableXML(@Valid ArrayTabulatedRequestDTO tabulatedFunctionRequest,
                                                      @Context HttpServletRequest request,
                                                      @Context HttpServletResponse response) {
        TabulatedFunctionFactory factory = cookieHandler.determineFabric(request, response);
        TabulatedFunction function = factory.create(
                tabulatedFunctionRequest.getXValues(),
                tabulatedFunctionRequest.getYValues()
        );

        return Response.ok(FunctionSerializer.serializeXml(function)).build();
    }
    @POST
    @Path("/createTabulatedFunctionWithFunctionByte")
    public Response createTabulatedFunctionWithFunctionByte(@Valid FunctionTabulatedFunctionRequestDTO tabulatedFunctionRequest,
                                                          @Context HttpServletRequest request,
                                                          @Context HttpServletResponse response) {
        TabulatedFunctionFactory factory = cookieHandler.determineFabric(request, response);
        System.out.println((tabulatedFunctionRequest.getFunctionName()));
        TabulatedFunction function = factory.create(
                FunctionSerializer.deserializeFunction(Base64.getDecoder().decode(tabulatedFunctionRequest.getFunctionName())),
                tabulatedFunctionRequest.getFrom(),
                tabulatedFunctionRequest.getTo(),
                tabulatedFunctionRequest.getAmountOfPoints()
        );
        return Response.ok(FunctionSerializer.serializeByte(function)).build();
    }
    @POST
    @Path("/createTabulatedFunctionWithFunctionJSON")
    public Response createTabulatedFunctionWithFunctionJSON(@Valid FunctionTabulatedFunctionRequestDTO tabulatedFunctionRequest,
                                                          @Context HttpServletRequest request,
                                                          @Context HttpServletResponse response) {
        TabulatedFunctionFactory factory = cookieHandler.determineFabric(request, response);
        System.out.println(tabulatedFunctionRequest.getFunctionName());
        TabulatedFunction function = factory.create(
                FunctionSerializer.deserializeFunction(Base64.getDecoder().decode(tabulatedFunctionRequest.getFunctionName())),
                tabulatedFunctionRequest.getFrom(),
                tabulatedFunctionRequest.getTo(),
                tabulatedFunctionRequest.getAmountOfPoints()
        );
        System.out.println(FunctionSerializer.serializeJson(function));
        return Response.ok(FunctionSerializer.serializeJson(function)).build();
    }
    @POST
    @Path("/createTabulatedFunctionWithFunctionXML")
    public Response createTabulatedFunctionWithFunctionXML(@Valid FunctionTabulatedFunctionRequestDTO tabulatedFunctionRequest,
                                                         @Context HttpServletRequest request,
                                                         @Context HttpServletResponse response) {
        TabulatedFunctionFactory factory = cookieHandler.determineFabric(request, response);
        TabulatedFunction function = factory.create(
                FunctionSerializer.deserializeFunction(Base64.getDecoder().decode(tabulatedFunctionRequest.getFunctionName())),
                tabulatedFunctionRequest.getFrom(),
                tabulatedFunctionRequest.getTo(),
                tabulatedFunctionRequest.getAmountOfPoints()
        );
        return Response.ok(FunctionSerializer.serializeXml(function)).build();
    }
}

