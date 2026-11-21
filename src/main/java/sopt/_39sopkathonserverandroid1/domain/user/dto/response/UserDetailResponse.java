package sopt._39sopkathonserverandroid1.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Locale;
import sopt._39sopkathonserverandroid1.domain.user.entity.User;
import sopt._39sopkathonserverandroid1.domain.user.enums.AgeCategory;

public record UserDetailResponse(
        @Schema(description = "사용자 이름", example = "김하나")
        String name,

        @Schema(description = "사용자 연령 그룹", example = "YOUTH")
        AgeCategory age,

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/profiles/1.jpg", nullable = true)
        String profileImageUrl,

        @Schema(description = "완료한 미션 수", example = "5")
        long completedMissionCount,

        @Schema(description = "작성한 미션 인증글에 받은 좋아요 수", example = "12")
        long receivedLikeCount,

        @Schema(description = "현재 달성 배지", example = "third",
                allowableValues = {"challenger", "third", "second", "first"})
        String badge
) {

    public static UserDetailResponse of(User user, long completedMissionCount, long receivedLikeCount) {
        return new UserDetailResponse(
                user.getName(),
                user.getAge(),
                user.getProfileImageUrl(),
                completedMissionCount,
                receivedLikeCount,
                user.getBadge().name().toLowerCase(Locale.ROOT)
        );
    }
}
