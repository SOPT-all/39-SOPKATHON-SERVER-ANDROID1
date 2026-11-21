package sopt._39sopkathonserverandroid1.domain.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import sopt._39sopkathonserverandroid1.domain.review.dto.response.ReviewListResponse;
import sopt._39sopkathonserverandroid1.global.dto.CommonResponse;

@Tag(name = "Review", description = "미션 인증글 관련 API")
public interface ReviewApi {

    @Operation(
            summary = "미션 인증글 목록 조회",
            description = "모든 사용자의 미션 인증글을 최신순으로 조회합니다. 좋아요 여부는 현재 사용자 ID 1을 기준으로 합니다."
    )
    CommonResponse<List<ReviewListResponse>> findReviews();
}
