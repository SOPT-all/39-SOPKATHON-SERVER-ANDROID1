package sopt._39sopkathonserverandroid1.domain.review.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt._39sopkathonserverandroid1.domain.review.code.ReviewSuccessCode;
import sopt._39sopkathonserverandroid1.domain.review.dto.response.ReviewListResponse;
import sopt._39sopkathonserverandroid1.domain.review.service.ReviewService;
import sopt._39sopkathonserverandroid1.global.dto.CommonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/reviews")
public class ReviewController implements ReviewApi {

    private final ReviewService reviewService;

    @Override
    @GetMapping
    public CommonResponse<List<ReviewListResponse>> findReviews() {
        List<ReviewListResponse> response = reviewService.findReviews();
        return CommonResponse.success(ReviewSuccessCode.REVIEW_LIST_FETCHED, response);
    }
}
