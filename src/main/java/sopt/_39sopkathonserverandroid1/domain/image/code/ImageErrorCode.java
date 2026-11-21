package sopt._39sopkathonserverandroid1.domain.image.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt._39sopkathonserverandroid1.global.code.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum ImageErrorCode implements ErrorCode {
    IMAGE_EMPTY(HttpStatus.BAD_REQUEST, "이미지 파일은 필수입니다."),
    IMAGE_TYPE_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "JPEG, PNG, WebP 이미지만 업로드할 수 있습니다."),
    IMAGE_SIZE_EXCEEDED(HttpStatus.PAYLOAD_TOO_LARGE, "이미지는 최대 15MB까지 업로드할 수 있습니다."),
    IMAGE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다. 다시 시도해 주세요.");

    private final HttpStatus status;
    private final String message;
}
