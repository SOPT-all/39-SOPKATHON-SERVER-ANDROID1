package sopt._39sopkathonserverandroid1.domain.image.exception;

import sopt._39sopkathonserverandroid1.domain.image.code.ImageErrorCode;
import sopt._39sopkathonserverandroid1.global.exception.BusinessException;

public class ImageEmptyException extends BusinessException {

    public ImageEmptyException() {
        super(ImageErrorCode.IMAGE_EMPTY);
    }
}
