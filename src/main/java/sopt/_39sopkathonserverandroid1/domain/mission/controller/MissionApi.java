package sopt._39sopkathonserverandroid1.domain.mission.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import sopt._39sopkathonserverandroid1.domain.mission.dto.request.MissionCreateRequest;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionCreateResponse;
import sopt._39sopkathonserverandroid1.domain.user.exception.UserNotFoundException;
import sopt._39sopkathonserverandroid1.global.dto.CommonResponse;
import sopt._39sopkathonserverandroid1.global.swagger.ApiExceptions;

@Tag(name = "미션", description = "미션 조회 및 수행 API")
public interface MissionApi {

    @Operation(summary = "미션 제작", description = "업로드한 이미지 키와 미션 내용을 등록합니다.")
    @ApiExceptions(UserNotFoundException.class)
    CommonResponse<MissionCreateResponse> createMission(
            @Parameter(description = "미션을 제작하는 사용자 ID", example = "1")
            @Positive(message = "유효하지 않은 값입니다.") Long userId,
            MissionCreateRequest request
    );
}
