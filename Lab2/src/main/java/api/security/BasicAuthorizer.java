package api.security;

import api.dto.UserDTO;
import io.dropwizard.auth.Authorizer;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.checkerframework.checker.nullness.qual.Nullable;
import persistence.entity.User;

public class BasicAuthorizer implements Authorizer<UserDTO> {
    @Override
    public boolean authorize(UserDTO user, String role, @Nullable ContainerRequestContext crt) {
        return user.getRoles() != null && user.getRoles().contains(role);
    }
}
