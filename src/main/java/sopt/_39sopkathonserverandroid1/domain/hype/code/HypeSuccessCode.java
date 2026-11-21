package sopt._39sopkathonserverandroid1.domain.hype.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sopt._39sopkathonserverandroid1.global.code.SuccessCode;

@Getter
@RequiredArgsConstructor
public enum HypeSuccessCode implements SuccessCode {
    HYPE_CREATED("하입이 등록되었습니다."),
    HYPE_DELETED("하입이 취소되었습니다.");

    private final String message;
}
