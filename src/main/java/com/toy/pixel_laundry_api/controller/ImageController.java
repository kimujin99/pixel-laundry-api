package com.toy.pixel_laundry_api.controller;

import com.toy.pixel_laundry_api.dto.response.ApiResponse;
import com.toy.pixel_laundry_api.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/images")
public class ImageController {

    @GetMapping("/test")
    public ResponseEntity<ApiResponse<Object>> test(WebRequest request) {
        return ResponseUtil.success(HttpStatus.OK, getUri(request), "Hello, world!");
    }

    private String getUri(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}
