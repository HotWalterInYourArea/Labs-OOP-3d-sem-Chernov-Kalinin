package ui.resources;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import functions.TabulatedFunction;
import functions.factory.FactoryType;
import functions.factory.TabulatedFunctionFactory;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import operations.IntegralFunctional;
import operations.TabulatedDifferentialOperator;
import operations.TabulatedFunctionOperationService;
import ui.dto.ArrayTabulatedRequestDTO;
import ui.service.TabulatedFunctionFactoryCookieHandler;

import java.util.Map;
import java.util.concurrent.ExecutionException;
@Path("operations")
public class TabulatedFunctionOperationResource {
    private final TabulatedFunctionFactoryCookieHandler cookieHandler=new TabulatedFunctionFactoryCookieHandler();
    private  Map<FactoryType, TabulatedFunctionFactory> factories;

    @POST
    @Path("/apply/{xValue}")
    public Response applyValue(@PathParam("xValue") double xValue,
                             @Valid ArrayTabulatedRequestDTO functionRequest,
                             @Context HttpServletRequest request,
                             @Context HttpServletResponse response) throws JsonProcessingException {
        TabulatedFunctionFactory factory = cookieHandler.determineFabric(request, response);
        TabulatedFunction function = factory.create(
                functionRequest.getXValues(),
                functionRequest.getYValues()
        );

        return Response.ok(new ObjectMapper().writeValueAsString(function.apply(xValue))).build();
    }
    @POST
    @Path("/doDifferential")
    public Response doDifferential(@Valid ArrayTabulatedRequestDTO tabulatedFunctionRequest,
                                 @Context HttpServletRequest request,
                                 @Context HttpServletResponse response) throws JsonProcessingException {
        Cookie[] cookies = request.getCookies();
        System.out.println(cookies);
        TabulatedFunctionFactory factory = cookieHandler.determineFabric(request, response);
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(factory);
        TabulatedFunction function = factory.create(
                tabulatedFunctionRequest.getXValues(),
                tabulatedFunctionRequest.getYValues()
        );
        return Response.ok(new ObjectMapper().writeValueAsString(operator.derive(function))).build();
    }

    @POST
    @Path("/doIntegral/{threads}")
    public Response doIntegral(@Valid ArrayTabulatedRequestDTO tabulatedFunctionRequest,
                             @PathParam("threads") int threadCount,
                             @Context HttpServletRequest request,
                             @Context HttpServletResponse response) throws JsonProcessingException, ExecutionException, InterruptedException {
        TabulatedFunctionFactory factory = cookieHandler.determineFabric(request, response);
        TabulatedFunction function = factory.create(
                tabulatedFunctionRequest.getXValues(),
                tabulatedFunctionRequest.getYValues()
        );
        IntegralFunctional operator = new IntegralFunctional(threadCount);
        return Response.ok(new ObjectMapper().writeValueAsString(operator.integrate(function))).build();
    }
}
