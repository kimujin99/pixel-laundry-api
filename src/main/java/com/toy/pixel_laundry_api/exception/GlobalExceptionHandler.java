package com.toy.pixel_laundry_api.exception;

import com.toy.pixel_laundry_api.dto.response.ApiResponse;
import com.toy.pixel_laundry_api.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleAllExceptions(Exception ex, HttpServletRequest request) {
        return ResponseUtil.error(
                HttpStatus.INTERNAL_SERVER_ERROR,
                request.getRequestURI(),
                "서버에서 오류가 발생했습니다: " + ex.getMessage()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        return ResponseUtil.error(
                HttpStatus.BAD_REQUEST,
                request.getRequestURI(),
                "서버에서 오류가 발생했습니다: " + ex.getMessage()
        );
    }

    @ExceptionHandler(MissingParameterException.class)
    public ResponseEntity<ApiResponse<Object>> handleMissingParameter(MissingParameterException ex, HttpServletRequest request) {
        return ResponseUtil.error(
                HttpStatus.BAD_REQUEST,
                request.getRequestURI(),
                ex.getMessage()
        );
    }

    @ExceptionHandler(InvalidParameterFormatException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidParameterFormat(InvalidParameterFormatException ex, HttpServletRequest request) {
        return ResponseUtil.error(
                HttpStatus.BAD_REQUEST,
                request.getRequestURI(),
                ex.getMessage()
        );
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<Object>> handleMaxSizeException(MaxUploadSizeExceededException ex, HttpServletRequest request) {
        return ResponseUtil.error(
                HttpStatus.PAYLOAD_TOO_LARGE,
                request.getRequestURI(),
                "파일 크기가 제한을 초과했습니다."
        );
    }

    @ExceptionHandler({ IOException.class, MultipartException.class })
    public ResponseEntity<ApiResponse<Object>> handleFileExceptions(Exception ex, HttpServletRequest request) {
        return ResponseUtil.error(
                HttpStatus.BAD_REQUEST,
                request.getRequestURI(),
                "파일 처리 중 오류가 발생했습니다: " + ex.getMessage()
        );
    }

    @ExceptionHandler({ SdkClientException.class, S3Exception.class })
    public ResponseEntity<ApiResponse<Object>> handleS3Exceptions(Exception ex, HttpServletRequest request) {
        return ResponseUtil.error(
                HttpStatus.SERVICE_UNAVAILABLE,
                request.getRequestURI(),
                "파일 저장소(R2) 연결에 문제가 발생했습니다."
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDatabaseException(DataIntegrityViolationException ex, HttpServletRequest request) {
        return ResponseUtil.error(
                HttpStatus.CONFLICT,
                request.getRequestURI(),
                "데이터베이스 제약 조건을 위반했습니다."
        );
    }

}
