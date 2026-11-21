package sopt._39sopkathonserverandroid1.domain.mission.exception;

import sopt._39sopkathonserverandroid1.domain.mission.code.MissionErrorCode;
import sopt._39sopkathonserverandroid1.global.exception.BusinessException;

public class MissionNotFoundException extends BusinessException {

    public MissionNotFoundException() {
        super(MissionErrorCode.MISSION_NOT_FOUND);
    }
}
