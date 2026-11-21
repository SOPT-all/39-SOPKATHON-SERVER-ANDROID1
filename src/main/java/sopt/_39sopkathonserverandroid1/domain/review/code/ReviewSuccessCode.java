package sopt._39sopkathonserverandroid1.domain.review.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sopt._39sopkathonserverandroid1.global.code.SuccessCode;

@Getter
@RequiredArgsConstructor
public enum ReviewSuccessCode implements SuccessCode {
    REVIEW_LIST_FETCHED("미션 인증글 목록 조회에 성공했습니다.");

    private final String message;
}
