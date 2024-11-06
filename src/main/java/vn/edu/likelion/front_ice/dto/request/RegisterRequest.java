package vn.edu.likelion.front_ice.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.common.enums.AccountType;
import vn.edu.likelion.front_ice.common.enums.Role;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {

    String firstName;
    String lastName;
    String email;
    String password;
    String confirmPassword;
    Role role;


}
