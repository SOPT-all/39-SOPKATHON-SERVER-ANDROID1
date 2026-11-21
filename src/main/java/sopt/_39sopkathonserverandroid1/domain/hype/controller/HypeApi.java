package sopt._39sopkathonserverandroid1.domain.hype.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.PathVariable;
import sopt._39sopkathonserverandroid1.domain.hype.dto.response.HypeToggleResponse;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionBasketNotFoundException;
import sopt._39sopkathonserverandroid1.global.dto.CommonResponse;
import sopt._39sopkathonserverandroid1.global.swagger.ApiExceptions;

@Tag(name = "Hype", description = "미션 하입 관련 API")
public interface HypeApi {

    @Operation(
            summary = "하입 토글",
            description = "현재 사용자 ID 1을 기준으로 미션 하입을 등록하거나 취소합니다."
    )
    @ApiExceptions(MissionBasketNotFoundException.class)
    CommonResponse<HypeToggleResponse> toggleHype(
            @Parameter(description = "하입을 토글할 미션 담기 기록 ID", required = true, example = "12")
            @PathVariable("missionBasketId")
            @Positive(message = "유효하지 않은 값입니다.") Long missionBasketId
    );
}
