package sopt._39sopkathonserverandroid1.domain.mission.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt._39sopkathonserverandroid1.domain.mission.code.MissionSuccessCode;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionDetailResponse;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionListResponse;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionNotFoundException;
import sopt._39sopkathonserverandroid1.domain.mission.service.MissionService;
import sopt._39sopkathonserverandroid1.global.dto.CommonResponse;
import sopt._39sopkathonserverandroid1.global.swagger.ApiExceptions;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions")
public class MissionController implements MissionApi {

    private final MissionService missionService;

    @Override
    @GetMapping
    public CommonResponse<List<MissionListResponse>> findMissions() {
        List<MissionListResponse> response = missionService.findMissions();

        return CommonResponse.success(
                MissionSuccessCode.MISSION_LIST_FETCHED,
                response
        );
    }

    @Override
    @GetMapping("/{missionId}")
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
