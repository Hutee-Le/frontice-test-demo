package vn.edu.likelion.front_ice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import vn.edu.likelion.front_ice.common.api.RestAPIResponse;

import java.io.IOException;
import java.util.Optional;

/**
 * CustomAuthenticationEntryPoint -
 *
 * @param
 * @return
 * @throws
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final AuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();

    @Autowired
    private ObjectMapper mapper;
    @Override public void commence(HttpServletRequest request, HttpServletResponse response,
                                   AuthenticationException authException) throws IOException, ServletException {
        this.delegate.commence(request, response, authException);
        response.setContentType("application/json;charset=UTF-8");

        RestAPIResponse<Object> res = new RestAPIResponse<>();
        res.setCode(HttpStatus.UNAUTHORIZED.value());

        // String errorMessage = Optional.ofNullable(authException.getCause()) // NULL
        //         .map(Throwable::getMessage)
        //         .orElse(authException.getMessage());
        res.setCode(HttpStatus.UNAUTHORIZED.value());

        res.setMessageEng("The token is invalid (expired, incorrect format, or JWT not included in the header)");
        res.setMessageVN("Token không hợp lệ (hết hạn, không đúng định dạng, hoặc không truyền JWT ở header)...");
        mapper.writeValue(response.getWriter(), res);
    }
}
