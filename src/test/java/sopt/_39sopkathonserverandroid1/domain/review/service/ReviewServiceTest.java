package sopt._39sopkathonserverandroid1.domain.review.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import sopt._39sopkathonserverandroid1.domain.image.service.MissionImageUrlService;
import sopt._39sopkathonserverandroid1.domain.review.dto.response.ReviewListResponse;
import sopt._39sopkathonserverandroid1.domain.review.repository.ReviewRepository;

class ReviewServiceTest {

    private final ReviewRepository reviewRepository = mock(ReviewRepository.class);
    private final MissionImageUrlService missionImageUrlService = mock(MissionImageUrlService.class);
    private final ReviewService reviewService = new ReviewService(reviewRepository, missionImageUrlService);

    @Test
    void findsReviewsWithLikeInformation() {
        LocalDateTime createdAt = LocalDateTime.of(2026, 11, 22, 14, 30);
        ReviewRepository.ReviewListProjection projection = projection(createdAt, 4L, 1L);
        when(missionImageUrlService.resolve("missions/review.jpg"))
                .thenReturn("https://example.com/reviews/1.jpg");
        when(reviewRepository.findAllWithLikeStatus(1L)).thenReturn(List.of(projection));

        List<ReviewListResponse> result = reviewService.findReviews();

        assertEquals(1, result.size());
        assertEquals(1L, result.getFirst().reviewId());
        assertEquals("https://example.com/reviews/1.jpg", result.getFirst().imageUrl());
        assertEquals(4, result.getFirst().likeCount());
        assertTrue(result.getFirst().isLiked());
    }

    @Test
    void returnsFalseWhenCurrentUserHasNotLikedReview() {
        ReviewRepository.ReviewListProjection projection = projection(LocalDateTime.now(), 0L, 0L);
        when(reviewRepository.findAllWithLikeStatus(1L)).thenReturn(List.of(projection));

        ReviewListResponse response = reviewService.findReviews().getFirst();

        assertEquals(0, response.likeCount());
        assertFalse(response.isLiked());
    }

    @Test
    void returnsEmptyListWhenThereAreNoReviews() {
        when(reviewRepository.findAllWithLikeStatus(1L)).thenReturn(List.of());

        assertEquals(List.of(), reviewService.findReviews());
    }

    private ReviewRepository.ReviewListProjection projection(
            LocalDateTime createdAt,
            Long likeCount,
            Long currentUserLikeCount
    ) {
        ReviewRepository.ReviewListProjection projection = mock(ReviewRepository.ReviewListProjection.class);
        when(projection.getReviewId()).thenReturn(1L);
        when(projection.getImageUrl()).thenReturn("missions/review.jpg");
        when(projection.getTitle()).thenReturn("셀프 사진 네 컷 찍어보기");
        when(projection.getContent()).thenReturn("처음엔 어색했는데 재밌었어요.");
        when(projection.getWriterName()).thenReturn("민수");
        when(projection.getCreatedAt()).thenReturn(createdAt);
        when(projection.getLikeCount()).thenReturn(likeCount);
        when(projection.getCurrentUserLikeCount()).thenReturn(currentUserLikeCount);
        return projection;
    }
}
