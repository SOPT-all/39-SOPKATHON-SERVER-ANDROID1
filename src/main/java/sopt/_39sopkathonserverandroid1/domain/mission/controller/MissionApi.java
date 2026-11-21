package sopt._39sopkathonserverandroid1.domain.mission.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionDetailResponse;
import sopt._39sopkathonserverandroid1.global.dto.CommonResponse;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionNotFoundException;
import sopt._39sopkathonserverandroid1.global.swagger.ApiExceptions;

@Tag(name = "Mission", description = "미션 관련 API")
public interface MissionApi {

    @Operation(
            summary = "미션 상세 조회",
            description = "미션 이미지, 작성자 이름, 제목, 설명, 진행 방법을 조회합니다."
    )
    @ApiExceptions({MissionNotFoundException.class})
    CommonResponse<MissionDetailResponse> getMissionDetail(
            @Parameter(description = "조회할 미션 ID", required = true)
            @PathVariable("missionId") Long missionId
    );
}
