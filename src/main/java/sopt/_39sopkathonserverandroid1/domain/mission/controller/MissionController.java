package sopt._39sopkathonserverandroid1.domain.mission.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sopt._39sopkathonserverandroid1.domain.mission.code.MissionSuccessCode;
import sopt._39sopkathonserverandroid1.domain.mission.dto.request.MissionCreateRequest;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionCreateResponse;
import sopt._39sopkathonserverandroid1.domain.mission.service.MissionService;
import sopt._39sopkathonserverandroid1.global.dto.CommonResponse;

@RestController
@RequiredArgsConstructor
public class MissionController implements MissionApi {

    private final MissionService missionService;

    @Override
    @PostMapping("/v1/users/{userId}/missions")
    public CommonResponse<MissionCreateResponse> createMission(
            @PathVariable("userId") Long userId,
            @Valid @RequestBody MissionCreateRequest request
    ) {
        MissionCreateResponse response = missionService.createMission(userId, request);
        return CommonResponse.success(MissionSuccessCode.MISSION_CREATED, response);
    }
}
