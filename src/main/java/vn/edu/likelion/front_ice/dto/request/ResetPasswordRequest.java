package vn.edu.likelion.front_ice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordRequest {
    String resetToken;
    String email;
    String newPassword;
    String confirmPassword;
}
