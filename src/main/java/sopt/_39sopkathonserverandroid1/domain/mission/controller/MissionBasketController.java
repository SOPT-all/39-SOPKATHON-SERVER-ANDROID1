package sopt._39sopkathonserverandroid1.domain.mission.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt._39sopkathonserverandroid1.domain.mission.code.MissionBasketSuccessCode;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.IncompleteMissionResponse;
import sopt._39sopkathonserverandroid1.domain.mission.service.MissionBasketService;
import sopt._39sopkathonserverandroid1.global.dto.CommonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users/{userId}/missions")
public class MissionBasketController implements MissionBasketApi {

    private final MissionBasketService missionBasketService;

    @Override
    @GetMapping("/incomplete")
    public CommonResponse<List<IncompleteMissionResponse>> findIncompleteMissions(
            @PathVariable("userId") Long userId
    ) {
        List<IncompleteMissionResponse> response = missionBasketService.findIncompleteMissions(userId);
        return CommonResponse.success(MissionBasketSuccessCode.INCOMPLETE_MISSIONS_FETCHED, response);
    }
}
