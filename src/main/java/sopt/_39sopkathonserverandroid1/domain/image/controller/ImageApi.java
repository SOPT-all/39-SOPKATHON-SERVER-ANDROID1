package sopt._39sopkathonserverandroid1.domain.image.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import sopt._39sopkathonserverandroid1.domain.image.dto.request.ImageUploadUrlRequest;
import sopt._39sopkathonserverandroid1.domain.image.dto.response.ImageUploadUrlResponse;
import sopt._39sopkathonserverandroid1.global.dto.CommonResponse;

@Tag(name = "이미지", description = "이미지 업로드 API")
public interface ImageApi {

    @Operation(
            summary = "미션 이미지 업로드 URL 발급",
            description = "안드로이드가 S3에 이미지를 직접 업로드할 수 있는 10분 유효 PUT URL을 발급합니다."
    )
    CommonResponse<ImageUploadUrlResponse> createMissionImageUploadUrl(ImageUploadUrlRequest request);
}
