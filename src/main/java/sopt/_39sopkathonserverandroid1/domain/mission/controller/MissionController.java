package sopt._39sopkathonserverandroid1.domain.mission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sopt._39sopkathonserverandroid1.domain.mission.code.MissionSuccessCode;
import sopt._39sopkathonserverandroid1.domain.mission.dto.request.MissionCreateRequest;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionCreateResponse;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionDetailResponse;
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
    @GetMapping("/missions/{missionId}")
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
}
