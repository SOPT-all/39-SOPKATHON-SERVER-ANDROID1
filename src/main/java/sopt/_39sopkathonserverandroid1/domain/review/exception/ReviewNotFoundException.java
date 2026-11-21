package sopt._39sopkathonserverandroid1.domain.review.exception;

import sopt._39sopkathonserverandroid1.domain.review.code.ReviewErrorCode;
import sopt._39sopkathonserverandroid1.global.exception.BusinessException;

public class ReviewNotFoundException extends BusinessException {

    public ReviewNotFoundException() {
        super(ReviewErrorCode.REVIEW_NOT_FOUND);
    }
}
