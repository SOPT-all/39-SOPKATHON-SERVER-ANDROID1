package sopt._39sopkathonserverandroid1.domain.image.service;

import java.time.Duration;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;
import sopt._39sopkathonserverandroid1.domain.image.dto.response.ImageUploadUrlResponse;

@Service
public class ImageService {

    private static final Duration UPLOAD_URL_EXPIRATION = Duration.ofMinutes(10);

    private final S3Presigner s3Presigner;
    private final String bucket;

    public ImageService(
            S3Presigner s3Presigner,
            @Value("${aws.s3.bucket}") String bucket
    ) {
        this.s3Presigner = s3Presigner;
        this.bucket = bucket;
    }

    public ImageUploadUrlResponse createMissionImageUploadUrl(String contentType) {
        String imageKey = "missions/" + UUID.randomUUID();
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(imageKey)
                .contentType(contentType)
                .build();
        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(UPLOAD_URL_EXPIRATION)
                .putObjectRequest(putObjectRequest)
                .build();
        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);

        return ImageUploadUrlResponse.of(presignedRequest.url().toString(), imageKey);
    }
}
