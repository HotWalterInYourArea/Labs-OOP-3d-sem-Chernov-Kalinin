package api.controller;

import api.dto.MathFunctionDTO;
import api.dto.PointDTO;
import api.service.MathFunctionService;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

@Path("/functions")
@AllArgsConstructor
@PermitAll
public class MathFunctionResource {
    private final MathFunctionService mathFunctionService=new MathFunctionService();
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response postMathFunction(@NotNull @Valid MathFunctionDTO mathFunctionDTO) {
        if(mathFunctionService.readById(mathFunctionDTO.getId())!=null){
            mathFunctionService.update(mathFunctionDTO);
            return Response.ok().build();
        }
        mathFunctionService.create(mathFunctionDTO);
        return Response.ok().build();
    }
    @GET
    @Path("/{HashCode}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response getMathFunction(@PathParam("HashCode") Long hashCode) {
        var func=mathFunctionService.readById(hashCode);
        Response.ResponseBuilder resp_builder=Response.status(Response.Status.BAD_REQUEST);
        if(func!=null) {
            return Response.ok(func).build();
        }
        return resp_builder.build();
    }
    @GET
    @Path("/{HashCode}/points")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response getMathFunctionPoints(@PathParam("HashCode") Long hashCode) {
        var func=mathFunctionService.readById(hashCode);
        Response.ResponseBuilder resp_builder=Response.status(Response.Status.BAD_REQUEST);
        if(func!=null) {
            var list=func.getListOfPoints();
            if(list!=null){
                return Response.ok(list).build();
            }
            return resp_builder.build();
        }
        return resp_builder.build();
    }
    @GET
    @Path("/{HashCode}/point")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response getMathFunctionPoint(@PathParam("HashCode") Long hashCode,@QueryParam("x") Double x) {
        var func=mathFunctionService.readById(hashCode);
        Response.ResponseBuilder resp_builder=Response.status(Response.Status.BAD_REQUEST);
        if(func!=null) {
            var list=func.getListOfPoints();
            if(list!=null){
                for(PointDTO p : list){
                    if(p.getX().equals(x)){
                        return Response.ok(p).build();
                    }
                }
                return resp_builder.build();
            }
            return resp_builder.build();
        }
        return resp_builder.build();
    }
    @PUT
    @Path("/{HashCode}")
    @UnitOfWork
    public Response putNewPoint(@PathParam("HashCode") Long hashCode,@NotNull @Valid PointDTO point){
        var func=mathFunctionService.readById(hashCode);
        if(func!=null) {
            point.setMathId(hashCode);
            for(PointDTO p : func.getListOfPoints()){
                if(p.getX().equals(point.getX())){
                    func.getListOfPoints().remove(p);
                    func.getListOfPoints().add(point);
                    mathFunctionService.update(func);
                    return Response.ok().build();
                }
            }
            func.getListOfPoints().add(point);
            mathFunctionService.update(func);
            return Response.ok().build();
        }
        Response.ResponseBuilder resp_builder=Response.status(Response.Status.BAD_REQUEST);
        return resp_builder.build();
    }
    @DELETE
    @Path("/{HashCode}")
    @UnitOfWork
    public Response deleteFunction(@PathParam("HashCode")Long hashCode) {
        var func = mathFunctionService.readById(hashCode);
        Response.ResponseBuilder resp_builder = Response.status(Response.Status.BAD_REQUEST);
        if (func != null) {
            mathFunctionService.delete(hashCode);
            return Response.ok().build();
        }
        return resp_builder.build();
    }
}
