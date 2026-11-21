package sopt._39sopkathonserverandroid1.domain.mission.exception;

import sopt._39sopkathonserverandroid1.domain.mission.code.MissionErrorCode;
import sopt._39sopkathonserverandroid1.global.exception.BusinessException;

public class MissionAiUnavailableException extends BusinessException {

    public MissionAiUnavailableException() {
        super(MissionErrorCode.MISSION_AI_UNAVAILABLE);
    }
}
