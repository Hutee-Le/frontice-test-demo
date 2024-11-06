package vn.edu.likelion.front_ice.common.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY) // Bất kỳ cái nào không có dữ liệu, thì không hiển thị
public class RestAPIResponse <T extends Object>  implements Serializable {
    private int status;
    private int code;
    private String messageEng;
    private String messageVN;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private  T data;
    private LocalDateTime timestamp;

    public RestAPIResponse(RestAPIStatus restApiStatus, T data) {

        if (restApiStatus == null) {
            throw new IllegalArgumentException("APIStatus must not be null");
        }
        this.status = restApiStatus.getCode();
        this.code = ErrorCode.OK.getCodeError();
        this.messageEng = restApiStatus.getDescription();
        this.data = data;
        this.timestamp = LocalDateTime.now();

    }

    public RestAPIResponse(RestAPIStatus restApiStatus, ErrorCode errorCode) {

        if (restApiStatus == null) {
            throw new IllegalArgumentException("APIStatus must not be null");
        }
        this.status = restApiStatus.getCode();
        this.code = errorCode.getCodeError();
        this.messageEng = errorCode.getMessageEng();
        this.messageVN = errorCode.getMessageVN();
        this.timestamp = LocalDateTime.now();

    }
}
