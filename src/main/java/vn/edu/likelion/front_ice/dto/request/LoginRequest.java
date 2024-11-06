package vn.edu.likelion.front_ice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * LoginRequest -
 *
 * @param
 * @return
 * @throws
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {

    String email;

    String password;
}
