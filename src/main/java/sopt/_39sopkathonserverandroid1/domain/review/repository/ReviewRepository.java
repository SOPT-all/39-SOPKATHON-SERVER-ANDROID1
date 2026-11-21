package sopt._39sopkathonserverandroid1.domain.review.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sopt._39sopkathonserverandroid1.domain.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = """
            SELECT
                r.id AS reviewId,
                r.image_url AS imageUrl,
                m.title AS title,
                r.content AS content,
                u.name AS writerName,
                r.created_at AS createdAt,
                COUNT(l.id) AS likeCount,
                COUNT(CASE WHEN l.user_id = :currentUserId THEN 1 END) AS currentUserLikeCount
            FROM reviews r
            JOIN mission_basket mb ON mb.id = r.mission_basket_id
            JOIN missions m ON m.id = mb.mission_id
            JOIN users u ON u.id = mb.user_id
            LEFT JOIN likes l ON l.review_id = r.id
            GROUP BY
                r.id,
                r.image_url,
                m.title,
                r.content,
                u.name,
                r.created_at
            ORDER BY r.created_at DESC, r.id DESC
            """, nativeQuery = true)
    List<ReviewListProjection> findAllWithLikeStatus(
            @Param("currentUserId") Long currentUserId
    );

    interface ReviewListProjection {

        Long getReviewId();

        String getImageUrl();

        String getTitle();

        String getContent();

        String getWriterName();

        LocalDateTime getCreatedAt();

        Long getLikeCount();

        Long getCurrentUserLikeCount();
    }
}
