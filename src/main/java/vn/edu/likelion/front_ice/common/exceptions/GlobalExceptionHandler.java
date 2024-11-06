package vn.edu.likelion.front_ice.common.exceptions;

import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.edu.likelion.front_ice.common.api.RestAPIResponse;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * GlobalExceptionHandler -
 *
 * @param
 * @return
 * @throws
 */

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity handlingRuntimeException(RuntimeException exception) {
        RestAPIResponse restAPIResponse = new RestAPIResponse();

        restAPIResponse.setStatus(ErrorCode.UNCATEGORIZED_EXCEPTION.getStatusCode());
        restAPIResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCodeError());
        restAPIResponse.setMessageEng(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessageEng());
        restAPIResponse.setMessageVN(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessageVN());

        restAPIResponse.setTimestamp(LocalDateTime.now());

        return ResponseEntity.badRequest().body(restAPIResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        RestAPIResponse restAPIResponse = new RestAPIResponse();
        restAPIResponse.setStatus(errorCode.getStatusCode());
        restAPIResponse.setCode(errorCode.getCodeError());
        restAPIResponse.setMessageEng(errorCode.getMessageEng());
        restAPIResponse.setMessageVN(errorCode.getMessageVN());
        restAPIResponse.setTimestamp(LocalDateTime.now());

        return ResponseEntity.badRequest().body(restAPIResponse);
    }

    // Anh Nhựt giúp em làm lại cái hàm này nhaa anh
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity handlingValidation(MethodArgumentNotValidException exception) {

        String enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        Map attributes = null;
        String fieldName = exception.getFieldError().getField(); // Lấy tên trường
        try {
            errorCode = ErrorCode.valueOf(enumKey);
            var constraintViolation =
                    exception.getBindingResult().getAllErrors().get(0).unwrap(ConstraintViolation.class);

            attributes = constraintViolation.getConstraintDescriptor().getAttributes();

            log.info(attributes.toString());

        } catch (IllegalArgumentException e) {
            // Bắn lỗi ở đây
            log.info(enumKey);

        }
        RestAPIResponse restAPIResponse = new RestAPIResponse();

        restAPIResponse.setStatus(errorCode.getStatusCode());
        restAPIResponse.setCode(errorCode.getCodeError());
        restAPIResponse.setTimestamp(LocalDateTime.now());

        // Gọi Exception theo tham số
        restAPIResponse.setMessageEng(
                Objects.nonNull(attributes) ? mapAttribute(errorCode.getMessageEng(), attributes, fieldName) :
                        errorCode.getMessageEng());
        restAPIResponse.setMessageVN(
                Objects.nonNull(attributes) ? mapAttribute(errorCode.getMessageVN(), attributes, fieldName) :
                errorCode.getMessageVN());

        return ResponseEntity.badRequest().body(restAPIResponse);
    }

    private String mapAttribute(String message, Map<String, Object> attributes, String fieldName) {
        // Thay thế placeholder {fieldName}
        message = message.replace("{fieldName}", fieldName.toUpperCase());

        // Duyệt qua tất cả các giá trị của enum ValidationAttribute
        for (ValidationAttribute attribute : ValidationAttribute.values()) {
            String key = attribute.getKey(); // Lấy tên thuộc tính từ enum

            if (attributes.containsKey(key)) {
                String value = String.valueOf(attributes.get(key)); // Lấy giá trị của thuộc tính từ map attributes
                message = message.replace("{" + key + "}", value); // Thay thế {min}, {max}, ...
            }
        }

        return message;
    }


}
