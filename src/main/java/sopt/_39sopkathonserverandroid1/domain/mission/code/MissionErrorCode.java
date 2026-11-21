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
    MISSION_BASKET_NOT_FOUND(HttpStatus.NOT_FOUND, "수행 중인 미션을 찾을 수 없습니다."),
    MISSION_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "인증할 이미지를 찾을 수 없습니다."),
    MISSION_ALREADY_COMPLETED(HttpStatus.CONFLICT, "이미 완료된 미션입니다."),
    MISSION_AI_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "이미지 인증 시간이 초과되었습니다. 다시 시도해 주세요."),
    MISSION_AI_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE,
            "현재 이미지 인증을 사용할 수 없습니다. 잠시 후 다시 시도해 주세요.");

    private final HttpStatus status;
    private final String message;
}
