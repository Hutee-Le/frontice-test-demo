package vn.edu.likelion.front_ice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import vn.edu.likelion.front_ice.common.api.ResponseUtil;
import vn.edu.likelion.front_ice.common.api.RestAPIResponse;
import vn.edu.likelion.front_ice.common.enums.AccountType;
import vn.edu.likelion.front_ice.common.enums.Role;
import vn.edu.likelion.front_ice.dto.request.LoginRequest;
import vn.edu.likelion.front_ice.dto.response.LoginResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.repository.AccountRepository;
import vn.edu.likelion.front_ice.security.SecurityUtil;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private ResponseUtil responseUtil;


    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        if ("google".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {
            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
            Map<String, Object> attributes = principal.getAttributes();

            // Lấy thông tin từ Google OAuth2
            String email = attributes.get("email").toString();
            String firstName = attributes.getOrDefault("given_name", "").toString();
            String lastName = attributes.getOrDefault("family_name", "").toString();
            String avatar = attributes.getOrDefault("picture", "").toString();

            // Kiểm tra xem người dùng đã tồn tại hay chưa
            Optional<AccountEntity> accountOptional = accountRepository.findByEmail(email);
            AccountEntity accountEntity;

            if (accountOptional.isPresent()) {
                // Nếu người dùng đã tồn tại, cập nhật thông tin
                accountEntity = accountOptional.get();
                accountEntity.setFirstName(firstName);
                accountEntity.setLastName(lastName);
                accountEntity.setAvatar(avatar);
                accountEntity.setStatus(1);
            } else {
                // Nếu người dùng chưa tồn tại, tạo mới
                String dummyPassword = UUID.randomUUID().toString();
                accountEntity = AccountEntity.builder()
                        .email(email)
                        .firstName(firstName)
                        .lastName(lastName)
                        .avatar(avatar)
                        .password(dummyPassword)
                        .status(1)
                        .role(Role.CHALLENGER)
                        .build();

                accountRepository.save(accountEntity);
            }

            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setEmail(email);
            loginRequest.setPassword("OAuth2Password");

            String accessToken = securityUtil.createAccessToken(authentication);
            String refreshToken = securityUtil.createRefreshToken(email, loginRequest);

            accountEntity.setRefreshToken(refreshToken);
            accountRepository.save(accountEntity);

            LoginResponse loginResponse = LoginResponse.builder()
                    .accessToken(accessToken)
                    .account(accountEntity)
                    .expiresIn(securityUtil.getExpirationTime())
                    .build();

            // Tạo và set refreshToken vào cookie
            ResponseCookie resCookie = ResponseCookie
                    .from("refresh_token", refreshToken)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(refreshTokenExpiration)
                    .build();

            // Sử dụng ResponseUtil để trả về response với cookie
            ResponseEntity<RestAPIResponse<Object>> responseEntity = responseUtil.successResponse(loginResponse, resCookie.toString());
            response.setContentType("application/json");
            response.getWriter().write(new ObjectMapper().writeValueAsString(responseEntity.getBody()));
        }
    }
}
