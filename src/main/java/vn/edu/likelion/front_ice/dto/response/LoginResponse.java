package vn.edu.likelion.front_ice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.entity.AccountEntity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponse {
    private String accessToken;
    private AccountEntity account;
    long expiresIn;



}
