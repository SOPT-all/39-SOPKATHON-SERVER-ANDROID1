package sopt._39sopkathonserverandroid1.domain.image.exception;

import sopt._39sopkathonserverandroid1.domain.image.code.ImageErrorCode;
import sopt._39sopkathonserverandroid1.global.exception.BusinessException;

public class ImageSizeExceededException extends BusinessException {

    public ImageSizeExceededException() {
        super(ImageErrorCode.IMAGE_SIZE_EXCEEDED);
    }
}
