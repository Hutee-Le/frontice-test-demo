package vn.edu.likelion.front_ice.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.common.enums.Role;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterResponse {
    String firstName;
    String lastName;
    String email;
    String password;
    Role role;
    int isAuthenticated;
}
