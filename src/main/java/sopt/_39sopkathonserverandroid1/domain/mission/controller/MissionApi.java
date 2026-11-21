package sopt._39sopkathonserverandroid1.domain.mission.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import sopt._39sopkathonserverandroid1.domain.mission.dto.request.MissionCreateRequest;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionCreateResponse;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionDetailResponse;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionListResponse;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionNotFoundException;
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
            @Valid MissionCreateRequest request
    );

    @Operation(
            summary = "홈 화면 전체 미션 목록 조회",
            description = "전체 미션을 Hype 개수 내림차순으로 조회합니다. Hype 개수가 같으면 미션 ID 내림차순으로 정렬합니다."
    )
    CommonResponse<List<MissionListResponse>> findMissions();

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
