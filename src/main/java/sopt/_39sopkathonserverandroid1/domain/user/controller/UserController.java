package sopt._39sopkathonserverandroid1.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt._39sopkathonserverandroid1.domain.user.code.UserSuccessCode;
import sopt._39sopkathonserverandroid1.domain.user.dto.response.UserDetailResponse;
import sopt._39sopkathonserverandroid1.domain.user.service.UserService;
import sopt._39sopkathonserverandroid1.global.dto.CommonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    @GetMapping("/{userId}")
    public CommonResponse<UserDetailResponse> findUserDetail(@PathVariable("userId") Long userId) {
        UserDetailResponse response = userService.findUserDetailOrThrow(userId);
        return CommonResponse.success(UserSuccessCode.USER_DETAIL_FETCHED, response);
    }
}
