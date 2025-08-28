package com.toy.pixel_laundry_api.controller;

import com.toy.pixel_laundry_api.dto.response.ApiResponse;
import com.toy.pixel_laundry_api.model.Images;
import com.toy.pixel_laundry_api.service.ImagesService;
import com.toy.pixel_laundry_api.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImagesController {

    private final ImagesService imagesService;

    @GetMapping("/test")
    public ResponseEntity<ApiResponse<Object>> test(HttpServletRequest request) {
        return ResponseUtil.success(HttpStatus.OK, request.getRequestURI(), "Hello, world!");
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "이미지 업로드", description = "클라이언트에서 이미지를 업로드하면 원본 이미지와 생성한 썸네일을 R2에 저장 후 메타데이터를 반환합니다.")
    public ResponseEntity<ApiResponse<Images>> uploadImage (
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request
    ) throws IOException {
        // 1️⃣ 파일 존재 여부 체크
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("업로드된 파일이 없습니다.");
        }

        // 2️⃣ Content-Type 체크
        if (!MediaType.MULTIPART_FORM_DATA_VALUE.equalsIgnoreCase(file.getContentType())) {
            throw new IllegalArgumentException("올바른 multipart/form-data 형식이 아닙니다.");
        }

        // 3️⃣ 서비스 호출 (비즈니스 로직 검증 포함)
        Images savedImage = imagesService.uploadImage(file);
        return ResponseUtil.success(HttpStatus.CREATED, request.getRequestURI(), savedImage);
    }

}
