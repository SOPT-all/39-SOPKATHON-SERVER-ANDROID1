package sopt._39sopkathonserverandroid1.domain.image.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import sopt._39sopkathonserverandroid1.domain.image.dto.response.ImageUploadUrlResponse;

class ImageServiceTest {

    @Test
    void createsMissionImageUploadUrl() {
        try (S3Presigner presigner = S3Presigner.builder()
                .region(Region.AP_SOUTHEAST_2)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("test-access-key", "test-secret-key")
                ))
                .build()) {
            ImageService imageService = new ImageService(presigner, "test-bucket");

            ImageUploadUrlResponse response = imageService.createMissionImageUploadUrl("image/jpeg");

            assertTrue(response.imageKey().startsWith("missions/"));
            assertFalse(response.uploadUrl().isBlank());
            assertEquals("test-bucket", response.uploadUrl().substring(
                    response.uploadUrl().indexOf("//") + 2,
                    response.uploadUrl().indexOf(".s3.")
            ));
        }
    }
}
