package com.toy.pixel_laundry_api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
@Builder
@Schema(description = "공통 API 응답")
public class ApiResponse<T> {

    @Schema(description = "응답 성공 여부", example = "true")
    private boolean success;
    @Schema(description = "응답 상태", example = "OK")
    private HttpStatus status;
    @Schema(description = "응답 상태 코드", example = "200")
    private int code;
    @Schema(description = "요청 주소", example = "/api/v1/*")
    private String path;
    @Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다.")
    private String message;
    @Schema(description = "응답 데이터")
    private T data;

}