package com.toy.pixel_laundry_api.service;

import com.toy.pixel_laundry_api.model.Images;
import com.toy.pixel_laundry_api.repository.ImagesRepository;
import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImagesService {

    private final S3Client s3Client;
    private final String r2BucketName;
    private final ImagesRepository imagesRepository;

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("png", "jpg", "jpeg", "gif", "bmp");

    public Images uploadImage(MultipartFile file) throws IOException {

        /* 예외 처리 */
        String originalName = file.getOriginalFilename();
        if (originalName == null || !originalName.contains(".")) {
            throw new IllegalArgumentException("유효한 파일명이 아닙니다.");
        }

        String extension = originalName.substring(originalName.lastIndexOf(".") + 1).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("허용되지 않은 확장자입니다: " + extension);
        }

        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        if (originalImage == null) {
            throw new IllegalArgumentException("실제 이미지 파일이 아닙니다.");
        }

        // UUID 생성
        String uuid = UUID.randomUUID().toString();
        String id = "IMG_" + uuid;

        // 이미지 변환 및 업로드
        Map<String, BufferedImage> imagesMap = new HashMap<>();
        imagesMap.put("original", originalImage);
        imagesMap.put("small", Scalr.resize(originalImage, 150));
        imagesMap.put("medium", Scalr.resize(originalImage, 300));
        imagesMap.put("large", Scalr.resize(originalImage, 600));
        imagesMap.put("blur", Scalr.apply(Scalr.resize(originalImage, 300)));

        for (Map.Entry<String, BufferedImage> entry : imagesMap.entrySet()) {
            String key = uuid + "/" + entry.getKey() + ".webp";

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(entry.getValue(), "webp", os);
            byte[] bytes = os.toByteArray();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(r2BucketName)
                    .key(key)
                    .contentType("image/webp")
                    .contentLength((long) bytes.length)
                    .build();

            try {
                s3Client.putObject(putObjectRequest, RequestBody.fromBytes(bytes));
            } catch (SdkClientException | S3Exception e) {
                throw new RuntimeException("R2 업로드 실패: " + entry.getKey(), e);
            }
        }

        // DB에 메타데이터 저장
        Images image = Images.builder()
                .uuid(id)
                .originalName(originalName)
                .extension(extension)
                .size(file.getSize())
                .build();

        return imagesRepository.save(image);
    }
}
