package sopt._39sopkathonserverandroid1.domain.hype.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record HypeToggleResponse(
        @Schema(description = "하입을 토글한 미션 담기 기록 ID", example = "12")
        Long missionBasketId,

        @Schema(description = "하입 대상 미션 ID", example = "3")
        Long missionId,

        @Schema(description = "현재 사용자의 하입 여부", example = "true")
        boolean isHyped,

        @Schema(description = "해당 미션의 전체 하입 수", example = "5")
        int hypeCount
) {

    public static HypeToggleResponse of(
            Long missionBasketId,
            Long missionId,
            boolean isHyped,
            long hypeCount
    ) {
        return new HypeToggleResponse(
                missionBasketId,
                missionId,
                isHyped,
                Math.toIntExact(hypeCount)
        );
    }
}
