package ui.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import functions.TabulatedFunction;
import io.FunctionsIO;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.*;
@Path("/converter")
@Produces(MediaType.APPLICATION_JSON)
public class ToFunctionConverterResource {
    @POST
    @Path("/convertFromBLOB")
    public String convertFromBLOB(@Valid byte[] bytes) throws JsonProcessingException {
        TabulatedFunction function = null;
        try(BufferedInputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(bytes))) {
            function = FunctionsIO.deserialize(inputStream);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return new ObjectMapper().writeValueAsString(function);
    }

    @POST
    @Path("/convertFromXML")
    public String convertFromXML(@Valid String xmlFunction) throws JsonProcessingException {
        System.out.println(xmlFunction);
        TabulatedFunction function = null;
        try(BufferedReader reader = new BufferedReader(new StringReader(xmlFunction))) {
            function = FunctionsIO.deserializeXml(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ObjectMapper().writeValueAsString(function);
    }
}
