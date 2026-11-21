package sopt._39sopkathonserverandroid1.domain.like.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sopt._39sopkathonserverandroid1.global.code.SuccessCode;

@Getter
@RequiredArgsConstructor
public enum LikeSuccessCode implements SuccessCode {
    LIKE_CREATED("좋아요가 등록되었습니다."),
    LIKE_DELETED("좋아요가 취소되었습니다.");

    private final String message;
}
