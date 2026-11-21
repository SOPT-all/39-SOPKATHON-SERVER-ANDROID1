package sopt._39sopkathonserverandroid1.domain.mission.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt._39sopkathonserverandroid1.global.code.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum MissionErrorCode implements ErrorCode {

    MISSION_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "존재하지 않는 미션입니다."
    ),
    MISSION_ALREADY_IN_BASKET(
            HttpStatus.CONFLICT,
            "이미 내 도전에 담긴 미션입니다."
    );

    private final HttpStatus status;
    private final String message;
}
