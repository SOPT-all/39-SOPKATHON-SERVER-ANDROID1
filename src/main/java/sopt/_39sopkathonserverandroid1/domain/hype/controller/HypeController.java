package sopt._39sopkathonserverandroid1.domain.hype.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt._39sopkathonserverandroid1.domain.hype.code.HypeSuccessCode;
import sopt._39sopkathonserverandroid1.domain.hype.dto.response.HypeToggleResponse;
import sopt._39sopkathonserverandroid1.domain.hype.service.HypeService;
import sopt._39sopkathonserverandroid1.global.dto.CommonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/mission-baskets")
public class HypeController implements HypeApi {

    private final HypeService hypeService;

    @Override
    @PostMapping("/{missionBasketId}/hypes")
    public CommonResponse<HypeToggleResponse> toggleHype(
            @PathVariable("missionBasketId") Long missionBasketId
    ) {
        HypeToggleResponse response = hypeService.toggleHype(missionBasketId);
        HypeSuccessCode successCode = response.isHyped()
                ? HypeSuccessCode.HYPE_CREATED
                : HypeSuccessCode.HYPE_DELETED;
        return CommonResponse.success(successCode, response);
    }
}
