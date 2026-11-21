package sopt._39sopkathonserverandroid1.domain.image.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt._39sopkathonserverandroid1.domain.image.code.ImageSuccessCode;
import sopt._39sopkathonserverandroid1.domain.image.dto.request.ImageUploadUrlRequest;
import sopt._39sopkathonserverandroid1.domain.image.dto.response.ImageUploadUrlResponse;
import sopt._39sopkathonserverandroid1.domain.image.service.ImageService;
import sopt._39sopkathonserverandroid1.global.dto.CommonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/images")
public class ImageController implements ImageApi {

    private final ImageService imageService;

    @Override
    @PostMapping("/presigned-url")
    public CommonResponse<ImageUploadUrlResponse> createMissionImageUploadUrl(
            @Valid @RequestBody ImageUploadUrlRequest request
    ) {
        ImageUploadUrlResponse response = imageService.createMissionImageUploadUrl(request.contentType());
        return CommonResponse.success(ImageSuccessCode.IMAGE_UPLOAD_URL_CREATED, response);
    }
}
