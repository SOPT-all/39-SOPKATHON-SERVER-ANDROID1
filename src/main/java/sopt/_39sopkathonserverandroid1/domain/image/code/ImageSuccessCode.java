package sopt._39sopkathonserverandroid1.domain.image.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sopt._39sopkathonserverandroid1.global.code.SuccessCode;

@Getter
@RequiredArgsConstructor
public enum ImageSuccessCode implements SuccessCode {
    IMAGE_UPLOAD_SUCCESS("이미지가 업로드되었습니다.");

    private final String message;
}
