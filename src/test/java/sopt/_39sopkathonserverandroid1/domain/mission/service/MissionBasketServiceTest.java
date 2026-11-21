package sopt._39sopkathonserverandroid1.domain.mission.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import sopt._39sopkathonserverandroid1.domain.image.service.MissionImageUrlService;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MyMissionResponse;
import sopt._39sopkathonserverandroid1.domain.mission.entity.Mission;
import sopt._39sopkathonserverandroid1.domain.mission.entity.MissionBasket;
import sopt._39sopkathonserverandroid1.domain.mission.repository.MissionBasketRepository;
import sopt._39sopkathonserverandroid1.domain.mission.repository.MissionRepository;
import sopt._39sopkathonserverandroid1.domain.user.exception.UserNotFoundException;
import sopt._39sopkathonserverandroid1.domain.user.repository.UserRepository;

class MissionBasketServiceTest {

    private final MissionBasketRepository missionBasketRepository = mock(MissionBasketRepository.class);
    private final MissionRepository missionRepository = mock(MissionRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final MissionImageUrlService missionImageUrlService = mock(MissionImageUrlService.class);
    private final MissionBasketService missionBasketService = new MissionBasketService(
            missionBasketRepository,
            missionRepository,
            userRepository,
            missionImageUrlService
    );

    @Test
    void findsIncompleteMissions() {
        MissionBasket basket = basket(12L, 3L, "셀프 사진 네 컷 찍어보기");
        when(userRepository.existsById(1L)).thenReturn(true);
        when(missionBasketRepository.findIncompleteByUserId(1L)).thenReturn(List.of(basket));
        when(missionImageUrlService.resolve("missions/3.jpg"))
                .thenReturn("https://example.com/missions/3.jpg");

        List<MyMissionResponse> response = missionBasketService.findIncompleteMissions(1L);

        assertEquals(1, response.size());
        assertEquals(12L, response.getFirst().missionBasketId());
        assertEquals(3L, response.getFirst().missionId());
        assertEquals("셀프 사진 네 컷 찍어보기", response.getFirst().title());
    }

    @Test
    void findsCompletedMissions() {
        MissionBasket basket = basket(8L, 2L, "동네 공원 산책하기");
        when(userRepository.existsById(1L)).thenReturn(true);
        when(missionBasketRepository.findCompletedByUserId(1L)).thenReturn(List.of(basket));
        when(missionImageUrlService.resolve("missions/2.jpg"))
                .thenReturn("https://example.com/missions/2.jpg");

        List<MyMissionResponse> response = missionBasketService.findCompletedMissions(1L);

        assertEquals(1, response.size());
        assertEquals(8L, response.getFirst().missionBasketId());
        assertEquals(2L, response.getFirst().missionId());
        assertEquals("동네 공원 산책하기", response.getFirst().title());
    }

    @Test
    void returnsEmptyCompletedListWhenThereAreNoCompletedMissions() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(missionBasketRepository.findCompletedByUserId(1L)).thenReturn(List.of());

        assertEquals(List.of(), missionBasketService.findCompletedMissions(1L));
    }

    @Test
    void throwsWhenUserDoesNotExist() {
        when(userRepository.existsById(99L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> missionBasketService.findCompletedMissions(99L));

        verifyNoInteractions(missionBasketRepository, missionImageUrlService);
    }

    private MissionBasket basket(Long basketId, Long missionId, String title) {
        LocalDateTime createdAt = LocalDateTime.of(2026, 11, 21, 18, 30);
        Mission mission = mock(Mission.class);
        when(mission.getTitle()).thenReturn(title);
        when(mission.getImageUrl()).thenReturn("missions/" + missionId + ".jpg");
        when(mission.getCreatedAt()).thenReturn(createdAt);

        MissionBasket basket = mock(MissionBasket.class);
        when(basket.getId()).thenReturn(basketId);
        when(basket.getMissionId()).thenReturn(missionId);
        when(basket.getMission()).thenReturn(mission);
        return basket;
    }
}
