package sopt._39sopkathonserverandroid1.domain.user.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sopt._39sopkathonserverandroid1.global.code.SuccessCode;

@Getter
@RequiredArgsConstructor
public enum UserSuccessCode implements SuccessCode {
    USER_DETAIL_FETCHED("사용자 상세 정보를 조회했습니다.");

    private final String message;
}
