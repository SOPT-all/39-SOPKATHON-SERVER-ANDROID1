package sopt._39sopkathonserverandroid1.domain.mission.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sopt._39sopkathonserverandroid1.global.code.SuccessCode;

@Getter
@RequiredArgsConstructor
public enum MissionSuccessCode implements SuccessCode {
    MISSION_CREATED("미션을 등록했습니다.");

    private final String message;
}
