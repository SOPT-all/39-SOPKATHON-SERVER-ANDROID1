package sopt._39sopkathonserverandroid1.domain.user.exception;

import sopt._39sopkathonserverandroid1.domain.user.code.UserErrorCode;
import sopt._39sopkathonserverandroid1.global.exception.BusinessException;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException() {
        super(UserErrorCode.USER_NOT_FOUND);
    }
}
