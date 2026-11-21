package sopt._39sopkathonserverandroid1.domain.mission.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import sopt._39sopkathonserverandroid1.domain.mission.entity.Mission;

public record MissionCreateResponse(
        @Schema(description = "등록된 미션 ID", example = "3")
        Long missionId
) {

    public static MissionCreateResponse of(Mission mission) {
        return new MissionCreateResponse(mission.getId());
    }
}
