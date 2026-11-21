package sopt._39sopkathonserverandroid1.domain.mission.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import java.util.List;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.IncompleteMissionResponse;
import sopt._39sopkathonserverandroid1.domain.user.exception.UserNotFoundException;
import sopt._39sopkathonserverandroid1.global.dto.CommonResponse;
import sopt._39sopkathonserverandroid1.global.swagger.ApiExceptions;

@Tag(name = "미션", description = "미션 조회 및 수행 API")
public interface MissionBasketApi {

    @Operation(
            summary = "내 미완료 미션 목록 조회",
            description = "해당 사용자가 담은 미션 중 미완료된 미션을 조회합니다."
    )
    @ApiExceptions(UserNotFoundException.class)
    CommonResponse<List<IncompleteMissionResponse>> findIncompleteMissions(
            @Parameter(description = "사용자 ID", example = "1")
            @Positive(message = "유효하지 않은 값입니다.") Long userId
    );
}
