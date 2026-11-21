package sopt._39sopkathonserverandroid1.domain.mission.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sopt._39sopkathonserverandroid1.domain.mission.code.MissionSuccessCode;
import sopt._39sopkathonserverandroid1.domain.mission.dto.request.MissionCompletionRequest;
import sopt._39sopkathonserverandroid1.domain.mission.dto.request.MissionCreateRequest;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionCompletionResponse;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionCreateResponse;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionDetailResponse;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionListResponse;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionNotFoundException;
import sopt._39sopkathonserverandroid1.domain.mission.service.MissionService;
import sopt._39sopkathonserverandroid1.global.dto.CommonResponse;
import sopt._39sopkathonserverandroid1.global.swagger.ApiExceptions;

@RestController
@RequiredArgsConstructor
public class MissionController implements MissionApi {

    private final MissionService missionService;

    @Override
    @PostMapping("/v1/users/{userId}/missions")
    public CommonResponse<MissionCreateResponse> createMission(
            @PathVariable("userId") Long userId,
            @RequestBody MissionCreateRequest request
    ) {
        MissionCreateResponse response = missionService.createMission(userId, request);
        return CommonResponse.success(MissionSuccessCode.MISSION_CREATED, response);
    }

    @Override
    @GetMapping("/v1/missions")
    public CommonResponse<List<MissionListResponse>> findMissions() {
        List<MissionListResponse> response = missionService.findMissions();

        return CommonResponse.success(
                MissionSuccessCode.MISSION_LIST_FETCHED,
                response
        );
    }

    @Override
    @GetMapping("/v1/missions/{missionId}")
    @ApiExceptions({MissionNotFoundException.class})
    public CommonResponse<MissionDetailResponse> getMissionDetail(
            @PathVariable("missionId") Long missionId
    ) {
        MissionDetailResponse response =
                missionService.getMissionDetail(missionId);

        return CommonResponse.success(
                MissionSuccessCode.MISSION_DETAIL_SUCCESS,
                response
        );
    }

    @Override
    @PostMapping("/v1/mission-baskets/{missionBasketId}/completion")
    public CommonResponse<MissionCompletionResponse> completeMission(
            @PathVariable @Positive(message = "missionBasketId는 양수여야 합니다.") Long missionBasketId,
            @Valid @RequestBody MissionCompletionRequest request
    ) {
        MissionCompletionResponse response = missionService.completeMission(missionBasketId, request);
        return CommonResponse.success(MissionSuccessCode.MISSION_COMPLETED, response);
    }
}
