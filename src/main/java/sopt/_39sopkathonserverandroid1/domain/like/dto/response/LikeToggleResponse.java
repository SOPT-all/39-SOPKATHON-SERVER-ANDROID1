package sopt._39sopkathonserverandroid1.domain.like.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record LikeToggleResponse(
        @Schema(description = "인증글 ID", example = "1")
        Long reviewId,

        @Schema(description = "현재 사용자의 좋아요 여부", example = "true")
        boolean isLiked,

        @Schema(description = "인증글 좋아요 수", example = "5")
        int likeCount
) {

    public static LikeToggleResponse of(Long reviewId, boolean isLiked, long likeCount) {
        return new LikeToggleResponse(reviewId, isLiked, Math.toIntExact(likeCount));
    }
}
