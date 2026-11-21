package sopt._39sopkathonserverandroid1.domain.mission.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sopt._39sopkathonserverandroid1.global.code.SuccessCode;

@Getter
@RequiredArgsConstructor
public enum MissionSuccessCode implements SuccessCode {
    MISSION_LIST_FETCHED("전체 미션 목록 조회에 성공했습니다."),
    MISSION_DETAIL_SUCCESS("미션 상세 조회에 성공했습니다.");

    private final String message;
}
