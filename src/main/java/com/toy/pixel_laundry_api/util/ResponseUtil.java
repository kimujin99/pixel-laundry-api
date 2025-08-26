package com.toy.pixel_laundry_api.util;

import com.toy.pixel_laundry_api.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static <T> ResponseEntity<ApiResponse<T>> success(HttpStatus status, String path, T data) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .success(true)
                .status(status)
                .code(status.value())
                .path(path)
                .message("요청이 성공적으로 처리되었습니다.")
                .data(data)
                .build();

        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(HttpStatus status, String path, String message) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .success(false)
                .status(status)
                .code(status.value())
                .path(path)
                .message(message)
                .data(null)
                .build();

        return ResponseEntity.status(status).body(response);
    }
}