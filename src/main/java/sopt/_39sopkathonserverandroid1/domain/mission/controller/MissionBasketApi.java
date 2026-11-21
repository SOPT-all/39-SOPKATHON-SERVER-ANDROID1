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
import sopt._39sopkathonserverandroid1.domain.mission.dto.request.MissionBasketCreateRequest;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.IncompleteMissionResponse;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionBasketCreateResponse;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionAlreadyInBasketException;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionNotFoundException;
import sopt._39sopkathonserverandroid1.domain.user.exception.UserNotFoundException;
import sopt._39sopkathonserverandroid1.global.dto.CommonResponse;
import sopt._39sopkathonserverandroid1.global.swagger.ApiExceptions;

@Tag(name = "미션", description = "미션 조회 및 수행 API")
public interface MissionBasketApi {

    @Operation(summary = "내 도전에 미션 담기", description = "선택한 미션을 사용자의 담은 미션 목록에 미완료 상태로 추가합니다.")
    @ApiResponse(
            responseCode = "201",
            description = "미션 담기 성공",
            useReturnTypeSchema = true,
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                      "status": 201,
                      "code": "MISSION_BASKET_CREATED",
                      "message": "내 도전에 미션을 담았습니다.",
                      "data": { "basketId": 1, "missionId": 3, "isCompleted": false }
                    }
                    """))
    )
    @ApiExceptions({UserNotFoundException.class, MissionNotFoundException.class, MissionAlreadyInBasketException.class})
    CommonResponse<MissionBasketCreateResponse> addMissionToBasket(
            @Parameter(description = "사용자 ID", example = "1")
            @Positive(message = "사용자 ID는 1 이상이어야 합니다.") Long userId,
            @Valid MissionBasketCreateRequest request
    );

    @Operation(
            summary = "내 미완료 미션 목록 조회",
            description = "해당 사용자가 담은 미완료 미션을 미션 등록일 최신순으로 조회합니다."
    )
    @ApiExceptions(UserNotFoundException.class)
    CommonResponse<List<IncompleteMissionResponse>> findIncompleteMissions(
            @Parameter(description = "사용자 ID", example = "1")
            @Positive(message = "유효하지 않은 값입니다.") Long userId
    );
}
