package sopt._39sopkathonserverandroid1.domain.mission.exception;

import sopt._39sopkathonserverandroid1.domain.mission.code.MissionErrorCode;
import sopt._39sopkathonserverandroid1.global.exception.BusinessException;

public class MissionBasketNotFoundException extends BusinessException {

    public MissionBasketNotFoundException() {
        super(MissionErrorCode.MISSION_BASKET_NOT_FOUND);
    }
}
