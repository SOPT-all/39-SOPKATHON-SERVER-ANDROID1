package sopt._39sopkathonserverandroid1.domain.image.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import sopt._39sopkathonserverandroid1.domain.image.dto.response.ImageUploadResponse;
import sopt._39sopkathonserverandroid1.domain.image.exception.ImageEmptyException;
import sopt._39sopkathonserverandroid1.domain.image.exception.ImageSizeExceededException;
import sopt._39sopkathonserverandroid1.domain.image.exception.ImageTypeNotSupportedException;
import sopt._39sopkathonserverandroid1.domain.image.exception.ImageUploadFailedException;

class ImageServiceTest {

    private S3Client s3Client;
    private S3Presigner s3Presigner;
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        s3Client = mock(S3Client.class);
        s3Presigner = S3Presigner.builder()
                .region(Region.AP_SOUTHEAST_2)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("test-access-key", "test-secret-key")
                ))
                .build();
        imageService = new ImageService(s3Client, s3Presigner, "test-bucket");
    }

    @AfterEach
    void tearDown() {
        s3Presigner.close();
    }

    @ParameterizedTest
    @MethodSource("supportedImages")
    void uploadsSupportedImage(String contentType, String extension, byte[] content) {
        MockMultipartFile file = new MockMultipartFile("file", "image." + extension, contentType, content);

        ImageUploadResponse response = imageService.uploadMissionImage(file);

        assertTrue(response.imageKey().matches("missions/[0-9a-f-]+\\." + extension));
        assertTrue(response.imageUrl().contains("X-Amz-Algorithm"));

        ArgumentCaptor<PutObjectRequest> requestCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
        verify(s3Client).putObject(requestCaptor.capture(), any(RequestBody.class));
        PutObjectRequest request = requestCaptor.getValue();
        assertEquals("test-bucket", request.bucket());
        assertEquals(response.imageKey(), request.key());
        assertEquals(contentType, request.contentType());
        assertEquals(content.length, request.contentLength());
    }

    @Test
    void rejectsEmptyImage() {
        MockMultipartFile file = new MockMultipartFile("file", new byte[0]);

        assertThrows(ImageEmptyException.class, () -> imageService.uploadMissionImage(file));

        verify(s3Client, never()).putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }

    @Test
    void rejectsOversizedImage() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getSize()).thenReturn(10L * 1024 * 1024 + 1);

        assertThrows(ImageSizeExceededException.class, () -> imageService.uploadMissionImage(file));

        verify(s3Client, never()).putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }

    @Test
    void rejectsUnsupportedContentType() {
        MockMultipartFile file = new MockMultipartFile("file", "image.gif", "image/gif", "GIF89a".getBytes());

        assertThrows(ImageTypeNotSupportedException.class, () -> imageService.uploadMissionImage(file));

        verify(s3Client, never()).putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }

    @Test
    void rejectsMismatchedImageSignature() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "fake.jpg", "image/jpeg", "not-a-jpeg".getBytes()
        );

        assertThrows(ImageTypeNotSupportedException.class, () -> imageService.uploadMissionImage(file));

        verify(s3Client, never()).putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }

    @Test
    void convertsS3FailureToBusinessException() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "image.jpg", "image/jpeg", new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF}
        );
        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class)))
                .thenThrow(S3Exception.builder().message("upload failed").build());

        assertThrows(ImageUploadFailedException.class, () -> imageService.uploadMissionImage(file));
    }

    private static Stream<Arguments> supportedImages() {
        return Stream.of(
                Arguments.of("image/jpeg", "jpg", new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF}),
                Arguments.of("image/png", "png",
                        new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A}),
                Arguments.of("image/webp", "webp",
                        new byte[]{0x52, 0x49, 0x46, 0x46, 0, 0, 0, 0, 0x57, 0x45, 0x42, 0x50})
        );
    }
}
