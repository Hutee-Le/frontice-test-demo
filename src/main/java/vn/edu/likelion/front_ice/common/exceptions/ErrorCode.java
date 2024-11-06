package vn.edu.likelion.front_ice.common.exceptions;

import lombok.Getter;
import vn.edu.likelion.front_ice.common.api.RestAPIStatus;

/**
 * ErrorCode - Định nghĩa lõi trong file này để tránh các lỗi trùng lặp khi những thành viên trong nhóm cùng làm 1 service
 *
 * @param
 * @return
 * @throws
 */
@Getter
public enum ErrorCode {

    OK(RestAPIStatus.OK,1200,"Success","OK","Thành công"),

    INVALID_KEY(RestAPIStatus.BAD_REQUEST,-100,"Invalid input object type!","Bad Request","Dữ liệu đầu vào không hợp lệ"),

    USERNAME_TOO_SHORT(RestAPIStatus.BAD_REQUEST, -101, "Username must be at least {min} characters", "Bad Request", "Tên người dùng phải có ít nhất {min} ký tự"),
    PASSWORD_NO_NUMBER(RestAPIStatus.BAD_REQUEST, -102, "Password must contain at least one number", "Bad Request", "Mật khẩu phải chứa ít nhất một số"),
    EMAIL_INVALID(RestAPIStatus.BAD_REQUEST, -103, "Email is invalid", "Bad Request", "Email không hợp lệ"),
    ACCOUNT_NOT_EXIST(RestAPIStatus.BAD_REQUEST, -104, "User not exist", "Bad Request", "Người dùng không tồn tại"),
    ACCOUNT_EXIST(RestAPIStatus.EXISTED,-105,"Account exist in system","Bad Request","Người dùng đã tồn tại"),
    ACCOUNT_OR_PASSWORD_INCORRECT(RestAPIStatus.BAD_REQUEST,-106,"Account or password incorrect","Bad Request","Tên tài khoản hoặc mật khẩu không đúng"),
    EMAIL_SENDING_FAILED(RestAPIStatus.BAD_REQUEST, -117, "Failed to send email", "Bad Request", "Gửi email thất bại"),
    ACCOUNT_UNAUTHENTICATED(RestAPIStatus.BAD_REQUEST, -118, "Account unauthenticated", "Bad Request", "Tài khoản chưa được xác thực OTP"),

    PHOTO_UPLOAD_FAILED(RestAPIStatus.BAD_REQUEST, -106, "Failed to save photo", "Bad Request", "Upload ảnh thất bại"),
    INVALID_DATE_FORMAT(RestAPIStatus.BAD_REQUEST, -107, "Invalid date format", "Bad Request", "Định dạng ngày không hợp lệ"),
    PARAM_INVALID(RestAPIStatus.BAD_REQUEST, -108, "{fieldName} is invalid", "Bad Request", "{fieldName} không hợp lệ"),


    PASSWORD_TOO_SHORT(RestAPIStatus.FAIL,-109,"Password must be between 8 and 20 characters","Fail","Mật khẩu phải từ 8 đến 20 ký tự"),
    PASSWORD_MISSING_UPPERCASE(RestAPIStatus.FAIL,-110,"Password must contain at least one uppercase letter","Fail","Mật khẩu phải chứa ít nhất một chữ cái in hoa"),
    PASSWORD_MISSING_LOWERCASE(RestAPIStatus.FAIL,111,"Password must be between 8 and 20 characters","Fail","Mật khẩu phải chứa ít nhất một chữ cái thường"),
    PASSWORD_MISSING_DIGIT(RestAPIStatus.FAIL,-112,"Password must contain at least one digit","Fail","Mật khẩu phải chứa ít nhất một chữ số"),
    PASSWORD_MISSING_SPECIAL(RestAPIStatus.FAIL,-113,"Password must contain at least one special character","Fail","Mật khẩu phải chứa ít nhất một ký tự đặc biệt"),
    PASSWORD_CONTAINS_WHITESPACE(RestAPIStatus.FAIL,-114,"Password must not contain whitespace","Fail","Mật khẩu không được chứa khoảng trắng"),
    CONFIRM_PASSWORD_NOT_MATCH(RestAPIStatus.BAD_REQUEST, -116, "Confirm Password not match", "Bad Request", "Mật khẩu xác thực không trùng khớp"),
    OLD_PASSWORD_INCORRECT(RestAPIStatus.BAD_REQUEST, -118, "Old password is incorrect", "Bad Request", "Mật khẩu cũ không chính xác"),
    OTP_INVALID(RestAPIStatus.BAD_REQUEST, -119, "OTP không hợp lệ", "Bad Request", "OTP không hợp lệ"),
    PASSWORD_RESET_FAILED(RestAPIStatus.FAIL, -120, "Đặt lại mật khẩu thất bại", "Fail", "Đặt lại mật khẩu thất bại"),
    RESET_TOKEN_EXPIRED(RestAPIStatus.BAD_REQUEST, -121, "Reset token expired", "Bad Request", "Mã đặt lại mật khẩu đã hết hạn"),
    RESET_TOKEN_INVALID(RestAPIStatus.BAD_REQUEST, -122, "Invalid reset token", "Bad Request", "Mã đặt lại mật khẩu không hợp lệ"),
    INVALID_REFRESH_TOKEN(RestAPIStatus.UNAUTHORIZED, 1002, "Refresh token is invalid or expired", "Unauthorized", "Token làm mới không hợp lệ hoặc hết hạn"),

    CHALLENGER_NOT_EXIST(RestAPIStatus.BAD_REQUEST,-201,"Challenger not exist","Bad Request","Challenger không tồn tại"),



    RECRUITER_NOT_EXIST(RestAPIStatus.BAD_REQUEST, -301,"Recruiter not exist","Bad Request","Recruiter không tồn tại"),

    SOLUTION_NOT_EXIST(RestAPIStatus.BAD_REQUEST, -401,"Solution not exist","Bad Request","Solution không tồn tại"),




    DELETE_FAILED(RestAPIStatus.CAN_NOT_DELETE,-9996,"Delete failed","Not found","Xoá thất bại"),

    UPDATE_FAILED(RestAPIStatus.BAD_REQUEST,-9997,"Update failed","Not found","Cập nhật thất bại"),
    QUERY_NOT_FOUND(RestAPIStatus.BAD_REQUEST,-9998,"Query not found","Not found","Truy vấn không thành công"),
    UNCATEGORIZED_EXCEPTION(RestAPIStatus.BAD_REQUEST,-9999,"Uncategorized error","Bad request","Lỗi chưa được định nghĩa");

    private final int statusCode;
    private final int codeError;
    private final String messageEng;
    private final String httpStatus;
    private final String messageVN;



    ErrorCode(RestAPIStatus restApiStatus ,int errorCode, String messageEng, String httpStatus, String messageVN) {
        this.statusCode = restApiStatus.getCode();
        this.codeError = errorCode;
        this.messageEng = messageEng;
        this.httpStatus = httpStatus;
        this.messageVN = messageVN;
    }

}
