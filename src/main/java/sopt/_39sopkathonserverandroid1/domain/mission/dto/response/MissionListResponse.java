package sopt._39sopkathonserverandroid1.domain.mission.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record MissionListResponse(
        @Schema(description = "미션 ID", example = "3")
        Long missionId,

        @Schema(description = "미션 이미지 URL", example = "https://example.com/missions/3.jpg")
        String imageUrl,

        @Schema(description = "미션 제목", example = "셀프 사진 네 컷 찍어보기")
        String title,

        @Schema(description = "미션이 받은 Hype 개수", example = "15")
        int hypeCount
) {

    public static MissionListResponse of(Long missionId, String imageUrl, String title, Long hypeCount) {
        return new MissionListResponse(
                missionId,
                imageUrl,
                title,
                Math.toIntExact(hypeCount)
        );
    }
}
