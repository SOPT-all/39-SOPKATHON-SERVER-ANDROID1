package sopt._39sopkathonserverandroid1.domain.like.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import sopt._39sopkathonserverandroid1.domain.like.dto.response.LikeToggleResponse;
import sopt._39sopkathonserverandroid1.domain.review.exception.InvalidReviewIdException;
import sopt._39sopkathonserverandroid1.domain.review.exception.ReviewNotFoundException;
import sopt._39sopkathonserverandroid1.global.dto.CommonResponse;
import sopt._39sopkathonserverandroid1.global.swagger.ApiExceptions;

@Tag(name = "Like", description = "인증글 좋아요 관련 API")
public interface LikeApi {

    @Operation(
            summary = "좋아요 토글",
            description = "현재 사용자 ID 1을 기준으로 인증글 좋아요를 등록하거나 취소합니다."
    )
    @ApiExceptions({ReviewNotFoundException.class, InvalidReviewIdException.class})
    CommonResponse<LikeToggleResponse> toggleLike(
            @Parameter(description = "좋아요를 토글할 인증글 ID", required = true, example = "1")
            @PathVariable("reviewId") Long reviewId
    );
}
