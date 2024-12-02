package api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Principal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Principal {
    @JsonProperty("Username")
    private String name;
    @JsonProperty("Password")
    private String password;
    @JsonProperty("Roles")
    private Set<String> roles;
}
