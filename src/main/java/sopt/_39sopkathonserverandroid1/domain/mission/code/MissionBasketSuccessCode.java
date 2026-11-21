package sopt._39sopkathonserverandroid1.domain.mission.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sopt._39sopkathonserverandroid1.global.code.SuccessCode;

@Getter
@RequiredArgsConstructor
public enum MissionBasketSuccessCode implements SuccessCode {
    INCOMPLETE_MISSIONS_FETCHED("미완료 미션 목록을 조회했습니다.");

    private final String message;
}
