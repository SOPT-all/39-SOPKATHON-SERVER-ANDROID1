package sopt._39sopkathonserverandroid1.domain.image.exception;

import sopt._39sopkathonserverandroid1.domain.image.code.ImageErrorCode;
import sopt._39sopkathonserverandroid1.global.exception.BusinessException;

public class ImageUploadFailedException extends BusinessException {

    public ImageUploadFailedException() {
        super(ImageErrorCode.IMAGE_UPLOAD_FAILED);
    }
}
