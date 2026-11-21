package sopt._39sopkathonserverandroid1.domain.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt._39sopkathonserverandroid1.domain.like.dto.response.LikeToggleResponse;
import sopt._39sopkathonserverandroid1.domain.like.entity.Like;
import sopt._39sopkathonserverandroid1.domain.like.repository.LikeRepository;
import sopt._39sopkathonserverandroid1.domain.review.exception.InvalidReviewIdException;
import sopt._39sopkathonserverandroid1.domain.review.exception.ReviewNotFoundException;
import sopt._39sopkathonserverandroid1.domain.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private static final Long CURRENT_USER_ID = 1L;

    private final LikeRepository likeRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public LikeToggleResponse toggleLike(Long reviewId) {
        validateReviewId(reviewId);
        validateReviewExists(reviewId);

        boolean isLiked = likeRepository.findByUserIdAndReviewId(CURRENT_USER_ID, reviewId)
                .map(like -> {
                    likeRepository.delete(like);
                    return false;
                })
                .orElseGet(() -> {
                    likeRepository.save(Like.create(CURRENT_USER_ID, reviewId));
                    return true;
                });

        long likeCount = likeRepository.countByReviewId(reviewId);
        return LikeToggleResponse.of(reviewId, isLiked, likeCount);
    }

    private void validateReviewId(Long reviewId) {
        if (reviewId == null || reviewId <= 0) {
            throw new InvalidReviewIdException();
        }
    }

    private void validateReviewExists(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new ReviewNotFoundException();
        }
    }
}
