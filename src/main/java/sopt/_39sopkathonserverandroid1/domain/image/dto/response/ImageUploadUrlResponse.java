package sopt._39sopkathonserverandroid1.domain.image.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ImageUploadUrlResponse(
        @Schema(description = "S3 이미지 업로드용 임시 URL")
        String uploadUrl,

        @Schema(description = "미션 등록 요청에 전달할 이미지 키", example = "missions/550e8400-e29b-41d4-a716-446655440000")
        String imageKey
) {

    public static ImageUploadUrlResponse of(String uploadUrl, String imageKey) {
        return new ImageUploadUrlResponse(uploadUrl, imageKey);
    }
}
