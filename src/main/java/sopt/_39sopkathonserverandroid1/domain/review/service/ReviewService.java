package sopt._39sopkathonserverandroid1.domain.review.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt._39sopkathonserverandroid1.domain.image.service.MissionImageUrlService;
import sopt._39sopkathonserverandroid1.domain.review.dto.response.ReviewListResponse;
import sopt._39sopkathonserverandroid1.domain.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private static final Long CURRENT_USER_ID = 1L;

    private final ReviewRepository reviewRepository;
    private final MissionImageUrlService missionImageUrlService;

    public List<ReviewListResponse> findReviews() {
        return reviewRepository.findAllWithLikeStatus(CURRENT_USER_ID).stream()
                .map(review -> ReviewListResponse.of(
                        review.getReviewId(),
                        missionImageUrlService.resolve(review.getImageUrl()),
                        review.getTitle(),
                        review.getContent(),
                        review.getWriterName(),
                        review.getCreatedAt(),
                        review.getLikeCount(),
                        review.getCurrentUserLikeCount()
                ))
                .toList();
    }
}
