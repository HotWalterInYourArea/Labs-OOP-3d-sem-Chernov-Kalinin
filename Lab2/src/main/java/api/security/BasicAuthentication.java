package api.security;

import api.dto.UserDTO;
import api.service.UserService;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Optional;
@NoArgsConstructor
@AllArgsConstructor
public class BasicAuthentication implements Authenticator<BasicCredentials, UserDTO> {
    private UserService userService=new UserService();
    @Override
    public Optional<UserDTO> authenticate(BasicCredentials credentials) throws AuthenticationException{
        return Optional.of(userService.readByPasswordId(credentials.getUsername(),credentials.getPassword()));
    }
}
