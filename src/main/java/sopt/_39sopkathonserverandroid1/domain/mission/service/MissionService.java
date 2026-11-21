package sopt._39sopkathonserverandroid1.domain.mission.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.S3Exception;
import sopt._39sopkathonserverandroid1.domain.image.service.MissionImageUrlService;
import sopt._39sopkathonserverandroid1.domain.mission.dto.request.MissionCompletionRequest;
import sopt._39sopkathonserverandroid1.domain.mission.dto.request.MissionCreateRequest;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionCompletionResponse;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionCreateResponse;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionDetailResponse;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionListResponse;
import sopt._39sopkathonserverandroid1.domain.mission.entity.Mission;
import sopt._39sopkathonserverandroid1.domain.mission.entity.MissionBasket;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionAlreadyCompletedException;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionBasketNotFoundException;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionImageNotFoundException;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionNotFoundException;
import sopt._39sopkathonserverandroid1.domain.mission.repository.MissionBasketRepository;
import sopt._39sopkathonserverandroid1.domain.mission.repository.MissionRepository;
import sopt._39sopkathonserverandroid1.domain.review.entity.Review;
import sopt._39sopkathonserverandroid1.domain.review.repository.ReviewRepository;
import sopt._39sopkathonserverandroid1.domain.user.exception.UserNotFoundException;
import sopt._39sopkathonserverandroid1.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;
    private final UserRepository userRepository;
    private final MissionImageUrlService missionImageUrlService;
    private final MissionBasketRepository missionBasketRepository;
    private final ReviewRepository reviewRepository;
    private final MissionImageVerifier missionImageVerifier;
    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Transactional
    public MissionCreateResponse createMission(Long userId, MissionCreateRequest request) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }

        Mission mission = Mission.create(
                userId, request.title(), request.description(), request.instructions(), request.imageKey()
        );
        return MissionCreateResponse.of(missionRepository.save(mission));
    }

    public List<MissionListResponse> findMissions() {
        return missionRepository.findAllOrderByHypeCountDesc().stream()
                .map(mission -> MissionListResponse.of(
                        mission.getMissionId(),
                        missionImageUrlService.resolve(mission.getImageUrl()),
                        mission.getTitle(),
                        mission.getHypeCount()
                ))
                .toList();
    }

    public MissionDetailResponse getMissionDetail(Long missionId) {
        Mission mission = missionRepository.findDetailById(missionId)
                .orElseThrow(MissionNotFoundException::new);

        return MissionDetailResponse.from(mission, missionImageUrlService.resolve(mission.getImageUrl()));
    }

    @Transactional
    public MissionCompletionResponse completeMission(Long missionBasketId, MissionCompletionRequest request) {
        MissionBasket missionBasket = missionBasketRepository.findById(missionBasketId)
                .orElseThrow(MissionBasketNotFoundException::new);
        if (missionBasket.isCompleted()) {
            throw new MissionAlreadyCompletedException();
        }

        ResponseBytes<GetObjectResponse> image = readImage(request.imageKey());
        Mission mission = missionBasket.getMission();
        String contentType = image.response().contentType();
        String missionDetails = mission.getDescription() + "\n수행 방법:\n" + mission.getInstructions();
        boolean passed = missionImageVerifier.verify(
                mission.getTitle(),
                missionDetails,
                image.asByteArray(),
                contentType != null ? contentType : contentTypeFrom(request.imageKey())
        );

        reviewRepository.save(Review.create(missionBasketId, request.imageKey(), request.content()));
        missionBasket.complete();
        return MissionCompletionResponse.of(passed);
    }

    private ResponseBytes<GetObjectResponse> readImage(String imageKey) {
        try {
            return s3Client.getObjectAsBytes(GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(imageKey)
                    .build());
        } catch (NoSuchKeyException exception) {
            throw new MissionImageNotFoundException();
        } catch (S3Exception exception) {
            if (exception.statusCode() == 404) {
                throw new MissionImageNotFoundException();
            }
            throw exception;
        }
    }

    private String contentTypeFrom(String imageKey) {
        if (imageKey.endsWith(".png")) {
            return "image/png";
        }
        if (imageKey.endsWith(".webp")) {
            return "image/webp";
        }
        return "image/jpeg";
    }
}
