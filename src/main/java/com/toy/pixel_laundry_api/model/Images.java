package com.toy.pixel_laundry_api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "IMAGES")
@Getter
@Builder
public class Images {
    @Id
    @Column(name = "UUID")
    @Schema(description = "이미지 고유 ID (IMG_ + UUID)", example = "IMG_550e8400-e29b-41d4-a716-446655440000")
    private String uuid;

    @Column(name = "ORIGINAL_NAME")
    @Schema(description = "이미지 원본 이름", example = "photo.png")
    private String originalName;

    @Column(name = "EXTENSION")
    @Schema(description = "이미지 확장자", example = "png")
    private String extension;

    @Column(name = "SIZE")
    @Schema(description = "이미지 크기 (바이트 단위)", example = "204800")
    private long size;

    @Column(name = "IS_DELETED")
    @Schema(description = "삭제 여부 (true = 삭제됨, false = 사용 가능)", example = "false")
    private boolean isDeleted;

    @Column(name = "CREATED_AT")
    @Schema(description = "이미지 생성 일시", example = "2025-08-28T13:30:00")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    @Schema(description = "이미지 수정 일시", example = "2025-08-28T15:45:00")
    private LocalDateTime updatedAt;
}
