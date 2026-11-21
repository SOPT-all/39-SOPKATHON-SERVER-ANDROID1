package sopt._39sopkathonserverandroid1.domain.mission.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MissionCompletionRequest(
        @NotBlank(message = "인증 사진은 필수입니다.")
        @Pattern(
                regexp = "^missions/[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}\\.(jpg|png|webp)$",
                message = "올바르지 않은 이미지 키입니다."
        )
        @Schema(
                description = "이미지 업로드 API에서 받은 S3 객체 키",
                example = "missions/550e8400-e29b-41d4-a716-446655440000.jpg"
        )
        String imageKey,

        @NotBlank(message = "한 줄 후기는 필수입니다.")
        @Size(max = 50, message = "한 줄 후기는 50자 이하로 입력해 주세요.")
        @Schema(description = "미션 수행 한 줄 후기", example = "네 컷 사진을 찍으니 재미있었어요!")
        String content
) {
}
