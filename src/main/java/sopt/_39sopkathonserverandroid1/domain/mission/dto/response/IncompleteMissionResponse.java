package sopt._39sopkathonserverandroid1.domain.mission.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import sopt._39sopkathonserverandroid1.domain.mission.entity.Mission;
import sopt._39sopkathonserverandroid1.domain.mission.entity.MissionBasket;

public record IncompleteMissionResponse(
        @Schema(description = "미션 담기 기록 ID", example = "12")
        Long missionBasketId,

        @Schema(description = "미션 ID", example = "3")
        Long missionId,

        @Schema(description = "미션 제목", example = "셀프 사진 네 컷 찍어보기")
        String title,

        @Schema(description = "미션 이미지 URL. 이미지가 없으면 null", example = "https://example.com/missions/3.jpg", nullable = true)
        String imageUrl
) {

    public static IncompleteMissionResponse of(MissionBasket basket) {
        Mission mission = basket.getMission();
        return new IncompleteMissionResponse(
                basket.getId(),
                basket.getMissionId(),
                mission.getTitle(),
                mission.getImageUrl()
        );
    }
}
