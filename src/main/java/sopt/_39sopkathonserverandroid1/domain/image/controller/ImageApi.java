package sopt._39sopkathonserverandroid1.domain.image.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;
import sopt._39sopkathonserverandroid1.domain.image.dto.response.ImageUploadResponse;
import sopt._39sopkathonserverandroid1.domain.image.exception.ImageEmptyException;
import sopt._39sopkathonserverandroid1.domain.image.exception.ImageSizeExceededException;
import sopt._39sopkathonserverandroid1.domain.image.exception.ImageTypeNotSupportedException;
import sopt._39sopkathonserverandroid1.domain.image.exception.ImageUploadFailedException;
import sopt._39sopkathonserverandroid1.global.dto.CommonResponse;
import sopt._39sopkathonserverandroid1.global.swagger.ApiExceptions;

@Tag(name = "이미지", description = "이미지 업로드 API")
public interface ImageApi {

    @Operation(
            summary = "미션 이미지 업로드",
            description = "안드로이드가 전송한 이미지를 서버가 S3에 업로드하고 imageKey와 화면 표시용 URL을 반환합니다."
    )
    @ApiExceptions({
            ImageEmptyException.class,
            ImageTypeNotSupportedException.class,
            ImageSizeExceededException.class,
            ImageUploadFailedException.class
    })
    CommonResponse<ImageUploadResponse> uploadMissionImage(
            @Parameter(description = "업로드할 JPEG, PNG 또는 WebP 이미지", required = true)
            MultipartFile file
    );
}
