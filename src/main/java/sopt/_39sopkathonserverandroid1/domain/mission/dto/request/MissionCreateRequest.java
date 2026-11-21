package sopt._39sopkathonserverandroid1.domain.mission.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MissionCreateRequest(
        @Schema(description = "미션 제목", example = "셀프 사진 네 컷 찍어보기",
                maxLength = 40, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "미션 제목을 입력해 주세요.")
        @Size(max = 40, message = "미션 제목은 40자 이하여야 합니다.")
        String title,

        @Schema(description = "미션을 추천하는 이유", example = "가까운 사진관에서 추억을 남겨요.",
                maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "미션 소개를 입력해 주세요.")
        @Size(max = 100, message = "미션 소개는 100자 이하여야 합니다.")
        String description,

        @Schema(description = "단계별 미션 진행 방법", example = "1. 사진관을 찾아요. 2. 네 컷 사진을 찍어요.",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "미션 수행 방법을 입력해 주세요.")
        String instructions,

        @Schema(description = "대표 사진 한 장을 업로드하고 받은 이미지 키",
                example = "missions/550e8400-e29b-41d4-a716-446655440000.jpg",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "미션 사진을 등록해 주세요.")
        @Pattern(regexp = "missions/[0-9a-f]{8}(?:-[0-9a-f]{4}){3}-[0-9a-f]{12}(?:\\.(?:jpg|jpeg|png|webp))?",
                message = "유효하지 않은 미션 이미지 키입니다.")
        String imageKey
) {
}
