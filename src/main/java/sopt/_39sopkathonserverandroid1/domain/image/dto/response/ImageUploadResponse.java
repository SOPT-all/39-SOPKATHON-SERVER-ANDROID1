package sopt._39sopkathonserverandroid1.domain.image.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ImageUploadResponse(
        @Schema(
                description = "미션 등록·인증 요청에 전달할 S3 객체 키",
                example = "missions/550e8400-e29b-41d4-a716-446655440000.jpg"
        )
        String imageKey,

        @Schema(description = "안드로이드 화면 표시용 임시 이미지 조회 URL")
        String imageUrl
) {

    public static ImageUploadResponse of(String imageKey, String imageUrl) {
        return new ImageUploadResponse(imageKey, imageUrl);
    }
}
