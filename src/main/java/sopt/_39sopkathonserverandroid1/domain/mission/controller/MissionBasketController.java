package sopt._39sopkathonserverandroid1.domain.mission.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sopt._39sopkathonserverandroid1.domain.mission.code.MissionBasketSuccessCode;
import sopt._39sopkathonserverandroid1.domain.mission.dto.request.MissionBasketCreateRequest;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionBasketCreateResponse;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MyMissionResponse;
import sopt._39sopkathonserverandroid1.domain.mission.service.MissionBasketService;
import sopt._39sopkathonserverandroid1.global.dto.CommonResponse;

@RestController
@RequiredArgsConstructor
public class MissionBasketController implements MissionBasketApi {

    private final MissionBasketService missionBasketService;

    @Override
    @PostMapping(path = "/v1/users/{userId}/mission-baskets", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<MissionBasketCreateResponse> addMissionToBasket(
            @PathVariable("userId") Long userId,
            @RequestBody MissionBasketCreateRequest request
    ) {
        MissionBasketCreateResponse response = missionBasketService.addMissionToBasket(userId, request.missionId());
        return CommonResponse.success(MissionBasketSuccessCode.MISSION_BASKET_CREATED, response);
    }

    @Override
    @GetMapping("/v1/users/{userId}/missions/incomplete")
    public CommonResponse<List<MyMissionResponse>> findIncompleteMissions(
            @PathVariable("userId") Long userId
    ) {
        List<MyMissionResponse> response = missionBasketService.findIncompleteMissions(userId);
        return CommonResponse.success(MissionBasketSuccessCode.INCOMPLETE_MISSIONS_FETCHED, response);
    }

    @Override
    @GetMapping("/v1/users/{userId}/missions/completed")
    public CommonResponse<List<MyMissionResponse>> findCompletedMissions(
            @PathVariable("userId") Long userId
    ) {
        List<MyMissionResponse> response = missionBasketService.findCompletedMissions(userId);
        return CommonResponse.success(MissionBasketSuccessCode.COMPLETED_MISSIONS_FETCHED, response);
    }
}
