package sopt._39sopkathonserverandroid1.domain.mission.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import sopt._39sopkathonserverandroid1.domain.mission.dto.request.MissionCompletionRequest;
import sopt._39sopkathonserverandroid1.domain.mission.dto.request.MissionCreateRequest;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionCompletionResponse;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionCreateResponse;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionDetailResponse;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionListResponse;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionAiTimeoutException;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionAiUnavailableException;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionAlreadyCompletedException;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionBasketNotFoundException;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionImageNotFoundException;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionNotFoundException;
import sopt._39sopkathonserverandroid1.domain.user.exception.UserNotFoundException;
import sopt._39sopkathonserverandroid1.global.dto.CommonResponse;
import sopt._39sopkathonserverandroid1.global.swagger.ApiExceptions;

@Tag(name = "미션", description = "미션 조회 및 수행 API")
public interface MissionApi {

    @Operation(
            summary = "미션 제작",
            description = "사진 한 장을 POST /v1/images로 업로드한 뒤, 받은 imageKey와 미션 제목, 소개, 진행 방법을 등록합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "미션 등록 성공",
            useReturnTypeSchema = true,
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                      "status": 200,
                      "code": "MISSION_CREATED",
                      "message": "미션을 등록했습니다.",
                      "data": { "missionId": 3 }
                    }
                    """))
    )
    @ApiResponse(responseCode = "415", description = "application/json 형식의 요청이 필요합니다.")
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

    @Operation(
            summary = "미션 수행 완료",
            description = "인증 사진을 Gemini로 판정하고 PASS/FAIL과 관계없이 후기와 완료 상태를 저장합니다."
    )
    @ApiExceptions({
            MissionBasketNotFoundException.class,
            MissionImageNotFoundException.class,
            MissionAlreadyCompletedException.class,
            MissionAiTimeoutException.class,
            MissionAiUnavailableException.class
    })
    CommonResponse<MissionCompletionResponse> completeMission(
            @Parameter(description = "완료할 미션 바구니 ID", required = true)
            @PathVariable("missionBasketId")
            @Positive(message = "missionBasketId는 양수여야 합니다.") Long missionBasketId,
            @Valid MissionCompletionRequest request
    );
}
