package sopt._39sopkathonserverandroid1.domain.image.service;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Service
public class MissionImageUrlService {

    private static final Duration READ_URL_TTL = Duration.ofMinutes(10);

    private final S3Presigner s3Presigner;
    private final String bucket;

    public MissionImageUrlService(
            S3Presigner s3Presigner,
            @Value("${aws.s3.bucket}") String bucket
    ) {
        this.s3Presigner = s3Presigner;
        this.bucket = bucket;
    }

    public String resolve(String imageReference) {
        if (imageReference == null || !imageReference.startsWith("missions/")) {
            return imageReference;
        }

        return s3Presigner.presignGetObject(request -> request
                .signatureDuration(READ_URL_TTL)
                .getObjectRequest(get -> get.bucket(bucket).key(imageReference)))
                .url()
                .toString();
    }
}
