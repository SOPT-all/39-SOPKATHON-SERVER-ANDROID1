package sopt._39sopkathonserverandroid1.global.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import sopt._39sopkathonserverandroid1.global.exception.BusinessException;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiExceptions {

    Class<? extends BusinessException>[] value();
}
