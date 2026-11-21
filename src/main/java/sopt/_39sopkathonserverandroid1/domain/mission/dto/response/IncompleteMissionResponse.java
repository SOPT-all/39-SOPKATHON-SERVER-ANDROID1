package sopt._39sopkathonserverandroid1.domain.mission.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import sopt._39sopkathonserverandroid1.domain.mission.entity.Mission;
import sopt._39sopkathonserverandroid1.domain.mission.entity.MissionBasket;

public record IncompleteMissionResponse(
        @Schema(description = "미션 담기 기록 ID", example = "12")
        Long missionBasketId,

        @Schema(description = "미션 ID", example = "3")
        Long missionId,

        @Schema(description = "미션 제목", example = "셀프 사진 네 컷 찍어보기")
        String title,

        @Schema(description = "미션 이미지 URL", example = "https://example.com/missions/3.jpg",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String imageUrl,

        @Schema(description = "미션 등록 일시", example = "2026-11-21T18:30:00")
        LocalDateTime createdAt
) {

    public static IncompleteMissionResponse of(MissionBasket basket, String imageUrl) {
        Mission mission = basket.getMission();
        return new IncompleteMissionResponse(
                basket.getId(),
                basket.getMissionId(),
                mission.getTitle(),
                imageUrl,
                mission.getCreatedAt()
        );
    }
}
