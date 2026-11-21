package sopt._39sopkathonserverandroid1.domain.mission.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionListResponse;
import sopt._39sopkathonserverandroid1.domain.mission.repository.MissionRepository;

class MissionServiceTest {

    private final MissionRepository missionRepository = mock(MissionRepository.class);
    private final MissionService missionService = new MissionService(missionRepository);

    @Test
    void findsMissionsOrderedByHypeCount() {
        MissionRepository.MissionListProjection first = projection(
                3L,
                "https://example.com/missions/3.jpg",
                "셀프 사진 네 컷 찍어보기",
                15L
        );
        MissionRepository.MissionListProjection second = projection(
                1L,
                "https://example.com/missions/1.jpg",
                "새로운 음식 도전하기",
                8L
        );
        when(missionRepository.findAllOrderByHypeCountDesc()).thenReturn(List.of(first, second));

        List<MissionListResponse> result = missionService.findMissions();

        assertEquals(List.of(
                MissionListResponse.of(3L, "https://example.com/missions/3.jpg", "셀프 사진 네 컷 찍어보기", 15L),
                MissionListResponse.of(1L, "https://example.com/missions/1.jpg", "새로운 음식 도전하기", 8L)
        ), result);
    }

    @Test
    void returnsEmptyListWhenThereAreNoMissions() {
        when(missionRepository.findAllOrderByHypeCountDesc()).thenReturn(List.of());

        assertEquals(List.of(), missionService.findMissions());
    }

    private MissionRepository.MissionListProjection projection(
            Long missionId,
            String imageUrl,
            String title,
            Long hypeCount
    ) {
        MissionRepository.MissionListProjection projection = mock(MissionRepository.MissionListProjection.class);
        when(projection.getMissionId()).thenReturn(missionId);
        when(projection.getImageUrl()).thenReturn(imageUrl);
        when(projection.getTitle()).thenReturn(title);
        when(projection.getHypeCount()).thenReturn(hypeCount);
        return projection;
    }
}
