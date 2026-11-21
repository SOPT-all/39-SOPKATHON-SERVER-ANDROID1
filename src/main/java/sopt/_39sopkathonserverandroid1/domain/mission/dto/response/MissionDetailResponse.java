package sopt._39sopkathonserverandroid1.domain.mission.dto.response;

import sopt._39sopkathonserverandroid1.domain.mission.entity.Mission;

public record MissionDetailResponse(
        String imageUrl,
        String writerName,
        String title,
        String description,
        String instructions
) {
    public static MissionDetailResponse from(Mission mission, String imageUrl) {
        return new MissionDetailResponse(
                imageUrl,
                mission.getUser().getName(),
                mission.getTitle(),
                mission.getDescription(),
                mission.getInstructions()
        );
    }
}
