package vn.edu.likelion.front_ice.common.api;
public enum RestAPIStatus {
    OK(200, "OK"),
    CREATED(201, "Create successfully."),
    FAIL(202, "Fail"),
    NON_AUTHORITATIVE_INFORMATION(203, ""),
    BAD_REQUEST(400, "Bad request"),
    UNAUTHORIZED(401, "Unauthorized or Access Token is expired"),
    INVALID_AUTHENTICATE_CREDENTIAL(402, "Invalid authenticated credential"),
    FORBIDDEN(403, "Forbidden! Access denied"),
    NOT_FOUND(404, "Not Found"),
    EXISTED(405, "Already existed"),
    BAD_PARAMS(406, "There is some invalid data"),
    EXPIRED(407, "Expired"),
    ERR_INVALID_EMAIL_FORMAT(408, "Invalid email format"),
    CAN_NOT_DELETE(409, "Can not delete"),
    CONFLICT(409, "Conflict"),
    INVALID_FILE(410, "Invalid file"),
    INTERNAL_SERVER_ERROR(500, "Internal server error"),
    INVALID_VERIFIED_CODE(409, "Invalid verified code"),
    INVALID_CREDENTIAL(401, "Lacking or incorrect credential"),
    MUlTIPLE_REQUEST_CODE(410, "You are request the auth code to fast"),
    VIETQR_API_ERROR(646, "VIET QR API has error"),
    UNPROCESSABLE(422,"Unprocessable Entity"),
    NOT_IMPLEMENTED(501,"Not Implemented");

    private final int code;
    private final String description;

    RestAPIStatus(int s, String v) {
        code = s;
        description = v;
    }

    public static RestAPIStatus getEnum(int code) {
        for (RestAPIStatus v : values())
            if (v.getCode() == code) return v;
        throw new IllegalArgumentException();
    }


    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}