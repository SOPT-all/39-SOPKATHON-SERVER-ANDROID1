package sopt._39sopkathonserverandroid1.domain.image.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import sopt._39sopkathonserverandroid1.domain.image.dto.response.ImageUploadResponse;
import sopt._39sopkathonserverandroid1.domain.image.exception.ImageEmptyException;
import sopt._39sopkathonserverandroid1.domain.image.exception.ImageSizeExceededException;
import sopt._39sopkathonserverandroid1.domain.image.exception.ImageTypeNotSupportedException;
import sopt._39sopkathonserverandroid1.domain.image.exception.ImageUploadFailedException;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ImageService {

    private static final long MAX_IMAGE_SIZE = 15L * 1024 * 1024;
    private static final Duration IMAGE_URL_EXPIRATION = Duration.ofHours(1);
    private static final Map<String, String> IMAGE_EXTENSIONS = Map.of(
            "image/jpeg", "jpg",
            "image/png", "png",
            "image/webp", "webp"
    );

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final String bucket;

    public ImageService(
            S3Client s3Client,
            S3Presigner s3Presigner,
            @Value("${aws.s3.bucket}") String bucket
    ) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
        this.bucket = bucket;
    }

    @Transactional
    public ImageUploadResponse uploadMissionImage(MultipartFile file) {
        String extension = validateImage(file);
        String imageKey = "missions/" + UUID.randomUUID() + "." + extension;

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(imageKey)
                .contentType(file.getContentType())
                .contentLength(file.getSize())
                .build();

        try {
            String imageUrl = createImageUrl(imageKey);
            try (InputStream inputStream = file.getInputStream()) {
                s3Client.putObject(request, RequestBody.fromInputStream(inputStream, file.getSize()));
            }
            return ImageUploadResponse.of(imageKey, imageUrl);
        } catch (IOException | SdkException exception) {
            log.error("S3 이미지 업로드에 실패했습니다. imageKey={}", imageKey, exception);
            throw new ImageUploadFailedException();
        }
    }

    private String validateImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ImageEmptyException();
        }
        if (file.getSize() > MAX_IMAGE_SIZE) {
            throw new ImageSizeExceededException();
        }

        String contentType = file.getContentType();
        String extension = IMAGE_EXTENSIONS.get(contentType);
        if (extension == null || !hasValidSignature(file, contentType)) {
            throw new ImageTypeNotSupportedException();
        }
        return extension;
    }

    private boolean hasValidSignature(MultipartFile file, String contentType) {
        try (InputStream inputStream = file.getInputStream()) {
            byte[] header = inputStream.readNBytes(12);
            return switch (contentType) {
                case "image/jpeg" -> startsWith(header, 0, new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF});
                case "image/png" -> startsWith(header, 0,
                        new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A});
                case "image/webp" -> startsWith(header, 0, new byte[]{0x52, 0x49, 0x46, 0x46})
                        && startsWith(header, 8, new byte[]{0x57, 0x45, 0x42, 0x50});
                default -> false;
            };
        } catch (IOException exception) {
            throw new ImageUploadFailedException();
        }
    }

    private boolean startsWith(byte[] source, int offset, byte[] expected) {
        if (source.length < offset + expected.length) {
            return false;
        }
        for (int index = 0; index < expected.length; index++) {
            if (source[offset + index] != expected[index]) {
                return false;
            }
        }
        return true;
    }

    private String createImageUrl(String imageKey) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(imageKey)
                .build();
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(IMAGE_URL_EXPIRATION)
                .getObjectRequest(getObjectRequest)
                .build();

        return s3Presigner.presignGetObject(presignRequest).url().toString();
    }
}
