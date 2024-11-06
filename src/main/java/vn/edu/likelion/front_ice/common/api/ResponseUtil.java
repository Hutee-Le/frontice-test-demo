package vn.edu.likelion.front_ice.common.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;

@Component
public class ResponseUtil {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    public ResponseUtil(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Create HTTP Response
     * @param restApiStatus
     * @param data
     * @return
     */
    private RestAPIResponse<Object> _createResponse(RestAPIStatus restApiStatus,Object data) {
        return new RestAPIResponse<>(restApiStatus, data);
    }


    private RestAPIResponse<Object> _createResponse(RestAPIStatus restApiStatus, ErrorCode errorCode) {
        return new RestAPIResponse<>(restApiStatus, errorCode);
    }

    /**
     * Build HTTP Response
     * @param restApiStatus
     * @param data
     * @param httpStatus
     * @return
     */
    public ResponseEntity<RestAPIResponse<Object>> buildResponse(RestAPIStatus restApiStatus, Object data, HttpStatus httpStatus) {
        return new ResponseEntity<>(_createResponse(restApiStatus, data), httpStatus);
    }

    public ResponseEntity<RestAPIResponse<Object>> buildResponse(RestAPIStatus restApiStatus, Object data, HttpStatus httpStatus, String responseCookies) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE,responseCookies);

        return new ResponseEntity<>(_createResponse(restApiStatus,data),httpHeaders,httpStatus);
    }


    /**
     *
     * @param restApiStatus
     * @param errorCode
     * @param httpStatus
     * @return
     */
    public ResponseEntity<RestAPIResponse<Object>> buildResponse(RestAPIStatus restApiStatus, ErrorCode errorCode, HttpStatus httpStatus) {
        return new ResponseEntity<>(_createResponse(restApiStatus, errorCode), httpStatus);
    }


    /**
     * Return success HTTP Request
     * @param data
     * @return
     */
    public ResponseEntity<RestAPIResponse<Object>> successResponse(Object data,String responseCookies) {
        return buildResponse(RestAPIStatus.OK, data,HttpStatus.OK,responseCookies);
    }

    public ResponseEntity<RestAPIResponse<Object>> successResponse() {
        return buildResponse(RestAPIStatus.OK, ErrorCode.OK, HttpStatus.OK);
    }

    public ResponseEntity<RestAPIResponse<Object>> successResponse(Object data) {
        return buildResponse(RestAPIStatus.OK, data, HttpStatus.OK);
    }
}
