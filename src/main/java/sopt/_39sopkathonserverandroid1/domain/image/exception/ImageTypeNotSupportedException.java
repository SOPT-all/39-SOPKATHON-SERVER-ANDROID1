package sopt._39sopkathonserverandroid1.domain.image.exception;

import sopt._39sopkathonserverandroid1.domain.image.code.ImageErrorCode;
import sopt._39sopkathonserverandroid1.global.exception.BusinessException;

public class ImageTypeNotSupportedException extends BusinessException {

    public ImageTypeNotSupportedException() {
        super(ImageErrorCode.IMAGE_TYPE_NOT_SUPPORTED);
    }
}
