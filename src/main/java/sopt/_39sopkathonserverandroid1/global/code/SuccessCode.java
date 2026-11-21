package sopt._39sopkathonserverandroid1.global.code;

import org.springframework.http.HttpStatus;

public interface SuccessCode {
    String name();

    String getMessage();

    default HttpStatus getStatus() {
        return HttpStatus.OK;
    }
}
