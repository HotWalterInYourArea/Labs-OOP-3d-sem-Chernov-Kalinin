package api.controller;
import api.dto.MathFunctionDTO;
import api.dto.UserDTO;
import api.service.UserService;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

@Path("/users")
@AllArgsConstructor
@RolesAllowed({"admin"})
public class UserResource {
    private final UserService userService=new UserService();
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response postUser(@NotNull @Valid UserDTO userDTO) {
        if(userService.readById(userDTO.getName())!=null){
            userService.update(userDTO);
            return Response.ok().build();
        }
        userService.create(userDTO);
        return Response.ok().build();
    }
    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response getUser(@PathParam("username") String username) {
        var user=userService.readById(username);
        Response.ResponseBuilder resp_builder=Response.status(Response.Status.BAD_REQUEST);
        if(user!=null) {
            return Response.ok(user).build();
        }
        return resp_builder.build();
    }
    @DELETE
    @Path("/{username}")
    @UnitOfWork
    public Response deleteUser(@PathParam("username")String username) {
        var user =userService.readById(username);
        Response.ResponseBuilder resp_builder = Response.status(Response.Status.BAD_REQUEST);
        if (user != null) {
            userService.delete(username);
            return Response.ok().build();
        }
        return resp_builder.build();
    }
    @PUT
    @UnitOfWork
    public Response updateUser(@Valid @NotNull UserDTO userDTO){
        userService.update(userDTO);
        return Response.ok().build();
    }
}
