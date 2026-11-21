package sopt._39sopkathonserverandroid1.domain.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record ReviewListResponse(
        @Schema(description = "인증글 ID", example = "1")
        Long reviewId,

        @Schema(description = "미션 인증 사진 URL", example = "https://example.com/reviews/1.jpg")
        String imageUrl,

        @Schema(description = "미션 제목", example = "셀프 사진 네 컷 찍어보기")
        String title,

        @Schema(description = "미션 후기", example = "처음엔 어색했는데 재밌었어요.")
        String content,

        @Schema(description = "인증글 작성자 이름", example = "민수")
        String writerName,

        @Schema(description = "인증글 작성 일시", example = "2026-11-22T14:30:00")
        LocalDateTime createdAt,

        @Schema(description = "인증글 좋아요 수", example = "4")
        int likeCount,

        @Schema(description = "현재 사용자의 좋아요 여부", example = "true")
        boolean isLiked
) {

    public static ReviewListResponse of(
            Long reviewId,
            String imageUrl,
            String title,
            String content,
            String writerName,
            LocalDateTime createdAt,
            Long likeCount,
            Long currentUserLikeCount
    ) {
        return new ReviewListResponse(
                reviewId,
                imageUrl,
                title,
                content,
                writerName,
                createdAt,
                Math.toIntExact(likeCount),
                currentUserLikeCount > 0
        );
    }
}
