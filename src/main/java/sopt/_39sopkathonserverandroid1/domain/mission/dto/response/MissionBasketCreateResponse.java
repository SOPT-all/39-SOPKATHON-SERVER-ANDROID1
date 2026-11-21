package sopt._39sopkathonserverandroid1.domain.mission.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import sopt._39sopkathonserverandroid1.domain.mission.entity.MissionBasket;

public record MissionBasketCreateResponse(
        @Schema(description = "생성된 미션 담기 기록 ID", example = "1")
        Long basketId,

        @Schema(description = "미션 ID", example = "3")
        Long missionId,

        @Schema(description = "미션 완료 여부", example = "false")
        boolean isCompleted
) {

    public static MissionBasketCreateResponse of(MissionBasket basket) {
        return new MissionBasketCreateResponse(basket.getId(), basket.getMissionId(), basket.isCompleted());
    }
}
