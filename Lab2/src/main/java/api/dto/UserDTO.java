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
    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final UserDTO userDTO = (UserDTO) obj;
        return this.name.equals(userDTO.getName()) && this.password.equals(userDTO.getPassword())&&this.roles.equals(userDTO.getRoles());
    }
}
