package sopt._39sopkathonserverandroid1.domain.image.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sopt._39sopkathonserverandroid1.domain.image.code.ImageSuccessCode;
import sopt._39sopkathonserverandroid1.domain.image.dto.response.ImageUploadResponse;
import sopt._39sopkathonserverandroid1.domain.image.service.ImageService;
import sopt._39sopkathonserverandroid1.global.dto.CommonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/images")
public class ImageController implements ImageApi {

    private final ImageService imageService;

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResponse<ImageUploadResponse> uploadMissionImage(
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        ImageUploadResponse response = imageService.uploadMissionImage(file);
        return CommonResponse.success(ImageSuccessCode.IMAGE_UPLOAD_SUCCESS, response);
    }
}
