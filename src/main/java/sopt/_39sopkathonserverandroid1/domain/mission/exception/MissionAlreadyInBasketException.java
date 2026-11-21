package sopt._39sopkathonserverandroid1.domain.mission.exception;

import sopt._39sopkathonserverandroid1.domain.mission.code.MissionErrorCode;
import sopt._39sopkathonserverandroid1.global.exception.BusinessException;

public class MissionAlreadyInBasketException extends BusinessException {

    public MissionAlreadyInBasketException() {
        super(MissionErrorCode.MISSION_ALREADY_IN_BASKET);
    }
}
