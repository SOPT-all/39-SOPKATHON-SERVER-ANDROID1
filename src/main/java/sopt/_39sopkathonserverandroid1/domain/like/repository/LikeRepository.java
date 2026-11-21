package sopt._39sopkathonserverandroid1.domain.like.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sopt._39sopkathonserverandroid1.domain.like.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserIdAndReviewId(Long userId, Long reviewId);

    long countByReviewId(Long reviewId);
}
