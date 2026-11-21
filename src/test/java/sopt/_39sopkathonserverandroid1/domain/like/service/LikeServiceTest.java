package sopt._39sopkathonserverandroid1.domain.like.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import sopt._39sopkathonserverandroid1.domain.like.dto.response.LikeToggleResponse;
import sopt._39sopkathonserverandroid1.domain.like.entity.Like;
import sopt._39sopkathonserverandroid1.domain.like.repository.LikeRepository;
import sopt._39sopkathonserverandroid1.domain.review.exception.InvalidReviewIdException;
import sopt._39sopkathonserverandroid1.domain.review.exception.ReviewNotFoundException;
import sopt._39sopkathonserverandroid1.domain.review.repository.ReviewRepository;

class LikeServiceTest {

    private final LikeRepository likeRepository = mock(LikeRepository.class);
    private final ReviewRepository reviewRepository = mock(ReviewRepository.class);
    private final LikeService likeService = new LikeService(likeRepository, reviewRepository);

    @Test
    void createsLikeWhenCurrentUserHasNotLikedReview() {
        when(reviewRepository.existsById(1L)).thenReturn(true);
        when(likeRepository.findByUserIdAndReviewId(1L, 1L)).thenReturn(Optional.empty());
        when(likeRepository.countByReviewId(1L)).thenReturn(5L);

        LikeToggleResponse response = likeService.toggleLike(1L);

        assertEquals(1L, response.reviewId());
        assertTrue(response.isLiked());
        assertEquals(5, response.likeCount());
        verify(likeRepository).save(any(Like.class));
        verify(likeRepository, never()).delete(any(Like.class));
    }

    @Test
    void deletesLikeWhenCurrentUserAlreadyLikedReview() {
        Like like = mock(Like.class);
        when(reviewRepository.existsById(1L)).thenReturn(true);
        when(likeRepository.findByUserIdAndReviewId(1L, 1L)).thenReturn(Optional.of(like));
        when(likeRepository.countByReviewId(1L)).thenReturn(4L);

        LikeToggleResponse response = likeService.toggleLike(1L);

        assertEquals(1L, response.reviewId());
        assertFalse(response.isLiked());
        assertEquals(4, response.likeCount());
        verify(likeRepository).delete(like);
        verify(likeRepository, never()).save(any(Like.class));
    }

    @Test
    void throwsWhenReviewDoesNotExist() {
        when(reviewRepository.existsById(99L)).thenReturn(false);

        assertThrows(ReviewNotFoundException.class, () -> likeService.toggleLike(99L));

        verifyNoInteractions(likeRepository);
    }

    @Test
    void throwsWhenReviewIdIsInvalid() {
        assertThrows(InvalidReviewIdException.class, () -> likeService.toggleLike(0L));

        verifyNoInteractions(reviewRepository, likeRepository);
    }
}
