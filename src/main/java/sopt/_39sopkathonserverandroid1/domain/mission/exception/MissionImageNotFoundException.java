package sopt._39sopkathonserverandroid1.domain.mission.exception;

import sopt._39sopkathonserverandroid1.domain.mission.code.MissionErrorCode;
import sopt._39sopkathonserverandroid1.global.exception.BusinessException;

public class MissionImageNotFoundException extends BusinessException {

    public MissionImageNotFoundException() {
        super(MissionErrorCode.MISSION_IMAGE_NOT_FOUND);
    }
}
