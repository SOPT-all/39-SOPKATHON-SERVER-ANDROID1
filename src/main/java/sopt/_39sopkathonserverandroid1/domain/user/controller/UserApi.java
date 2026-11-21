package sopt._39sopkathonserverandroid1.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import sopt._39sopkathonserverandroid1.domain.user.dto.response.UserDetailResponse;
import sopt._39sopkathonserverandroid1.domain.user.exception.UserNotFoundException;
import sopt._39sopkathonserverandroid1.global.dto.CommonResponse;
import sopt._39sopkathonserverandroid1.global.swagger.ApiExceptions;

@Tag(name = "사용자", description = "사용자 정보 API")
public interface UserApi {

    @Operation(summary = "사용자 상세 정보 조회",
            description = "마이페이지에 표시할 프로필, 완료 미션 수, 받은 좋아요 수와 배지를 조회합니다.")
    @ApiResponse(
            responseCode = "200",
            description = "사용자 상세 정보 조회 성공",
            useReturnTypeSchema = true,
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                      "status": 200,
                      "code": "USER_DETAIL_FETCHED",
                      "message": "사용자 상세 정보를 조회했습니다.",
                      "data": {
                        "name": "김하나",
                        "age": "YOUTH",
                        "profileImageUrl": "https://example.com/profiles/1.jpg",
                        "completedMissionCount": 5,
                        "receivedLikeCount": 12,
                        "badge": "third"
                      }
                    }
                    """))
    )
    @ApiExceptions(UserNotFoundException.class)
    CommonResponse<UserDetailResponse> findUserDetail(
            @Parameter(description = "사용자 ID", example = "1")
            @Positive(message = "유효하지 않은 값입니다.") Long userId
    );
}
