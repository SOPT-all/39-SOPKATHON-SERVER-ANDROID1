package sopt._39sopkathonserverandroid1.global.dto;

import sopt._39sopkathonserverandroid1.global.code.ErrorCode;
import sopt._39sopkathonserverandroid1.global.code.GlobalErrorCode;
import sopt._39sopkathonserverandroid1.global.code.SuccessCode;

public record CommonResponse<T>(
        int status,
        String code,
        String message,
        T data
) {

    public static <T> CommonResponse<T> success(SuccessCode successCode, T data) {
        return new CommonResponse<>(successCode.getStatus().value(), successCode.name(), successCode.getMessage(), data);
    }

    public static CommonResponse<Void> success(SuccessCode successCode) {
        return success(successCode, null);
    }

    public static CommonResponse<Void> error(ErrorCode errorCode) {
        return new CommonResponse<>(errorCode.getStatus().value(), errorCode.name(), errorCode.getMessage(), null);
    }

    public static CommonResponse<Void> validationError(String validationErrorMessage) {
        GlobalErrorCode errorCode = GlobalErrorCode.INVALID_INPUT;
        return new CommonResponse<>(errorCode.getStatus().value(), errorCode.name(), validationErrorMessage, null);
    }

}
