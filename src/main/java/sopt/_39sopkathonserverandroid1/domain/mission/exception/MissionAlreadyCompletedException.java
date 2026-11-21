package sopt._39sopkathonserverandroid1.domain.mission.exception;

import sopt._39sopkathonserverandroid1.domain.mission.code.MissionErrorCode;
import sopt._39sopkathonserverandroid1.global.exception.BusinessException;

public class MissionAlreadyCompletedException extends BusinessException {

    public MissionAlreadyCompletedException() {
        super(MissionErrorCode.MISSION_ALREADY_COMPLETED);
    }
}
