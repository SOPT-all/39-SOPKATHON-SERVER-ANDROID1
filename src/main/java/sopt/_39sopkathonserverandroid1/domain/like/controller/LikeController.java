package sopt._39sopkathonserverandroid1.domain.like.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt._39sopkathonserverandroid1.domain.like.code.LikeSuccessCode;
import sopt._39sopkathonserverandroid1.domain.like.dto.response.LikeToggleResponse;
import sopt._39sopkathonserverandroid1.domain.like.service.LikeService;
import sopt._39sopkathonserverandroid1.global.dto.CommonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/reviews")
public class LikeController implements LikeApi {

    private final LikeService likeService;

    @Override
    @PostMapping("/{reviewId}/likes")
    public CommonResponse<LikeToggleResponse> toggleLike(
            @PathVariable("reviewId") Long reviewId
    ) {
        LikeToggleResponse response = likeService.toggleLike(reviewId);
        LikeSuccessCode successCode = response.isLiked()
                ? LikeSuccessCode.LIKE_CREATED
                : LikeSuccessCode.LIKE_DELETED;

        return CommonResponse.success(successCode, response);
    }
}
