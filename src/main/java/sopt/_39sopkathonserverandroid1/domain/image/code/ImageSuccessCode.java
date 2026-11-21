package sopt._39sopkathonserverandroid1.domain.image.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sopt._39sopkathonserverandroid1.global.code.SuccessCode;

@Getter
@RequiredArgsConstructor
public enum ImageSuccessCode implements SuccessCode {
    IMAGE_UPLOAD_URL_CREATED("이미지 업로드 URL이 발급되었습니다.");

    private final String message;
}
