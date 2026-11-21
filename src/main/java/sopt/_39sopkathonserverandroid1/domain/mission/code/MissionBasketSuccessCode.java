package sopt._39sopkathonserverandroid1.domain.mission.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt._39sopkathonserverandroid1.global.code.SuccessCode;

@Getter
@RequiredArgsConstructor
public enum MissionBasketSuccessCode implements SuccessCode {
    INCOMPLETE_MISSIONS_FETCHED(HttpStatus.OK, "미완료 미션 목록을 조회했습니다."),
    COMPLETED_MISSIONS_FETCHED(HttpStatus.OK, "완료 미션 목록을 조회했습니다."),
    MISSION_BASKET_CREATED(HttpStatus.CREATED, "내 도전에 미션을 담았습니다.");

    private final HttpStatus status;
    private final String message;
}
