package vn.edu.likelion.front_ice.common.exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException {
    private ErrorCode errorCode;
    private Object data;

    public AppException(ErrorCode errorCode){
        super(errorCode.getMessageEng());
        this.errorCode = errorCode;

    }
}
