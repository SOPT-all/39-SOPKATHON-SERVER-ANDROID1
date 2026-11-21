package sopt._39sopkathonserverandroid1.domain.mission.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import sopt._39sopkathonserverandroid1.domain.image.service.MissionImageUrlService;
import sopt._39sopkathonserverandroid1.domain.mission.dto.request.MissionCompletionRequest;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionCompletionResponse.VerificationResult;
import sopt._39sopkathonserverandroid1.domain.mission.entity.Mission;
import sopt._39sopkathonserverandroid1.domain.mission.entity.MissionBasket;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionAiTimeoutException;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionAlreadyCompletedException;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionBasketNotFoundException;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionImageNotFoundException;
import sopt._39sopkathonserverandroid1.domain.mission.repository.MissionBasketRepository;
import sopt._39sopkathonserverandroid1.domain.mission.repository.MissionRepository;
import sopt._39sopkathonserverandroid1.domain.review.entity.Review;
import sopt._39sopkathonserverandroid1.domain.review.repository.ReviewRepository;
import sopt._39sopkathonserverandroid1.domain.user.repository.UserRepository;

class MissionCompletionServiceTest {

    private static final String IMAGE_KEY = "missions/550e8400-e29b-41d4-a716-446655440000.jpg";

    private MissionBasketRepository missionBasketRepository;
    private ReviewRepository reviewRepository;
    private MissionImageVerifier missionImageVerifier;
    private S3Client s3Client;
    private MissionService missionService;
    private MissionBasket missionBasket;

    @BeforeEach
    void setUp() {
        missionBasketRepository = mock(MissionBasketRepository.class);
        reviewRepository = mock(ReviewRepository.class);
        missionImageVerifier = mock(MissionImageVerifier.class);
        s3Client = mock(S3Client.class);
        missionService = new MissionService(
                mock(MissionRepository.class),
                mock(UserRepository.class),
                mock(MissionImageUrlService.class),
                missionBasketRepository,
                reviewRepository,
                missionImageVerifier,
                s3Client
        );
        ReflectionTestUtils.setField(missionService, "bucket", "test-bucket");

        missionBasket = mock(MissionBasket.class);
        Mission mission = mock(Mission.class);
        when(missionBasketRepository.findById(1L)).thenReturn(Optional.of(missionBasket));
        when(missionBasket.getMission()).thenReturn(mission);
        when(mission.getTitle()).thenReturn("네 컷 사진 찍기");
        when(mission.getDescription()).thenReturn("친구와 네 컷 사진을 촬영한다.");
        when(mission.getInstructions()).thenReturn("사진관에서 포즈를 정하고 촬영한다.");
        when(s3Client.getObjectAsBytes(any(GetObjectRequest.class))).thenReturn(ResponseBytes.fromByteArray(
                GetObjectResponse.builder().contentType("image/jpeg").build(),
                new byte[]{1, 2, 3}
        ));
    }

    @ParameterizedTest
    @MethodSource("verificationResults")
    void completesMissionRegardlessOfVerificationResult(boolean passed, VerificationResult expected) {
        when(missionImageVerifier.verify(any(), any(), any(), any())).thenReturn(passed);

        var response = missionService.completeMission(
                1L,
                new MissionCompletionRequest(IMAGE_KEY, "재미있었어요!")
        );

        assertEquals(expected, response.verificationResult());
        ArgumentCaptor<Review> reviewCaptor = ArgumentCaptor.forClass(Review.class);
        verify(reviewRepository).save(reviewCaptor.capture());
        assertEquals(1L, reviewCaptor.getValue().getMissionBasketId());
        assertEquals(IMAGE_KEY, reviewCaptor.getValue().getImageKey());
        assertEquals("재미있었어요!", reviewCaptor.getValue().getContent());
        verify(missionBasket).complete();
    }

    @Test
    void doesNotCompleteMissionWhenAiTimesOut() {
        when(missionImageVerifier.verify(any(), any(), any(), any()))
                .thenThrow(new MissionAiTimeoutException());

        assertThrows(MissionAiTimeoutException.class,
                () -> missionService.completeMission(1L, new MissionCompletionRequest(IMAGE_KEY, "후기")));

        verify(reviewRepository, never()).save(any(Review.class));
        verify(missionBasket, never()).complete();
    }

    @Test
    void rejectsMissingMissionBasket() {
        when(missionBasketRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(MissionBasketNotFoundException.class,
                () -> missionService.completeMission(2L, new MissionCompletionRequest(IMAGE_KEY, "후기")));
    }

    @Test
    void rejectsAlreadyCompletedMission() {
        when(missionBasket.isCompleted()).thenReturn(true);

        assertThrows(MissionAlreadyCompletedException.class,
                () -> missionService.completeMission(1L, new MissionCompletionRequest(IMAGE_KEY, "후기")));

        verify(s3Client, never()).getObjectAsBytes(any(GetObjectRequest.class));
    }

    @Test
    void rejectsMissingS3Image() {
        when(s3Client.getObjectAsBytes(any(GetObjectRequest.class)))
                .thenThrow(NoSuchKeyException.builder().message("missing").build());

        assertThrows(MissionImageNotFoundException.class,
                () -> missionService.completeMission(1L, new MissionCompletionRequest(IMAGE_KEY, "후기")));

        verify(missionImageVerifier, never()).verify(any(), any(), any(), any());
    }

    private static Stream<Arguments> verificationResults() {
        return Stream.of(
                Arguments.of(true, VerificationResult.PASS),
                Arguments.of(false, VerificationResult.FAIL)
        );
    }
}
