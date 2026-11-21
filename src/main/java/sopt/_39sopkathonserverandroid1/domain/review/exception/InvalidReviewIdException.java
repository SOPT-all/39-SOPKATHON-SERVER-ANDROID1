package sopt._39sopkathonserverandroid1.domain.review.exception;

import sopt._39sopkathonserverandroid1.domain.review.code.ReviewErrorCode;
import sopt._39sopkathonserverandroid1.global.exception.BusinessException;

public class InvalidReviewIdException extends BusinessException {

    public InvalidReviewIdException() {
        super(ReviewErrorCode.INVALID_REQUEST);
    }
}
