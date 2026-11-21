package sopt._39sopkathonserverandroid1.domain.hype.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import sopt._39sopkathonserverandroid1.domain.hype.dto.response.HypeToggleResponse;
import sopt._39sopkathonserverandroid1.domain.hype.entity.Hype;
import sopt._39sopkathonserverandroid1.domain.hype.repository.HypeRepository;
import sopt._39sopkathonserverandroid1.domain.mission.entity.MissionBasket;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionBasketNotFoundException;
import sopt._39sopkathonserverandroid1.domain.mission.repository.MissionBasketRepository;

class HypeServiceTest {

    private final HypeRepository hypeRepository = mock(HypeRepository.class);
    private final MissionBasketRepository missionBasketRepository = mock(MissionBasketRepository.class);
    private final HypeService hypeService = new HypeService(hypeRepository, missionBasketRepository);

    @Test
    void createsHypeWhenHypeDoesNotExist() {
        MissionBasket missionBasket = missionBasket(12L, 3L);
        when(missionBasketRepository.findByIdAndUserId(12L, 1L)).thenReturn(Optional.of(missionBasket));
        when(hypeRepository.findByMissionBasketId(12L)).thenReturn(Optional.empty());
        when(hypeRepository.countRecommendedByMissionId(3L)).thenReturn(5L);

        HypeToggleResponse response = hypeService.toggleHype(12L);

        assertTrue(response.isHyped());
        assertEquals(5, response.hypeCount());
        assertEquals(3L, response.missionId());
        verify(hypeRepository).save(any(Hype.class));
    }

    @Test
    void cancelsHypeWhenHypeIsRecommended() {
        MissionBasket missionBasket = missionBasket(12L, 3L);
        Hype hype = Hype.create(12L);
        when(missionBasketRepository.findByIdAndUserId(12L, 1L)).thenReturn(Optional.of(missionBasket));
        when(hypeRepository.findByMissionBasketId(12L)).thenReturn(Optional.of(hype));
        when(hypeRepository.countRecommendedByMissionId(3L)).thenReturn(4L);

        HypeToggleResponse response = hypeService.toggleHype(12L);

        assertFalse(response.isHyped());
        assertFalse(hype.isRecommended());
        assertEquals(4, response.hypeCount());
    }

    @Test
    void registersHypeAgainWhenHypeWasCanceled() {
        MissionBasket missionBasket = missionBasket(12L, 3L);
        Hype hype = Hype.create(12L);
        hype.toggleRecommendation();
        when(missionBasketRepository.findByIdAndUserId(12L, 1L)).thenReturn(Optional.of(missionBasket));
        when(hypeRepository.findByMissionBasketId(12L)).thenReturn(Optional.of(hype));
        when(hypeRepository.countRecommendedByMissionId(3L)).thenReturn(5L);

        HypeToggleResponse response = hypeService.toggleHype(12L);

        assertTrue(response.isHyped());
        assertTrue(hype.isRecommended());
        assertEquals(5, response.hypeCount());
    }

    @Test
    void throwsWhenMissionBasketDoesNotBelongToCurrentUser() {
        when(missionBasketRepository.findByIdAndUserId(99L, 1L)).thenReturn(Optional.empty());

        assertThrows(MissionBasketNotFoundException.class, () -> hypeService.toggleHype(99L));

        verifyNoInteractions(hypeRepository);
    }

    private MissionBasket missionBasket(Long missionBasketId, Long missionId) {
        MissionBasket missionBasket = mock(MissionBasket.class);
        when(missionBasket.getId()).thenReturn(missionBasketId);
        when(missionBasket.getMissionId()).thenReturn(missionId);
        return missionBasket;
    }
}
