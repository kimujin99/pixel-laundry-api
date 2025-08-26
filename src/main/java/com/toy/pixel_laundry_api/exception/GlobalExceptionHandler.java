package com.toy.pixel_laundry_api.exception;

import com.toy.pixel_laundry_api.dto.response.ApiResponse;
import com.toy.pixel_laundry_api.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleAllExceptions(Exception ex, WebRequest request) {
        return ResponseUtil.error(
                HttpStatus.INTERNAL_SERVER_ERROR,
                getUri(request),
                "서버에서 오류가 발생했습니다: " + ex.getMessage()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        return ResponseUtil.error(
                HttpStatus.BAD_REQUEST,
                getUri(request),
                "서버에서 오류가 발생했습니다: " + ex.getMessage()
        );
    }

    @ExceptionHandler(MissingParameterException.class)
    public ResponseEntity<ApiResponse<Object>> handleMissingParameter(MissingParameterException ex, WebRequest request) {
        return ResponseUtil.error(
                HttpStatus.BAD_REQUEST,
                getUri(request),
                ex.getMessage()
        );
    }

    @ExceptionHandler(InvalidParameterFormatException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidParameterFormat(InvalidParameterFormatException ex, WebRequest request) {
        return ResponseUtil.error(
                HttpStatus.BAD_REQUEST,
                getUri(request),
                ex.getMessage()
        );
    }

    private String getUri(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}
