package sopt._39sopkathonserverandroid1.domain.hype.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt._39sopkathonserverandroid1.domain.hype.dto.response.HypeToggleResponse;
import sopt._39sopkathonserverandroid1.domain.hype.entity.Hype;
import sopt._39sopkathonserverandroid1.domain.hype.repository.HypeRepository;
import sopt._39sopkathonserverandroid1.domain.mission.entity.MissionBasket;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionBasketNotFoundException;
import sopt._39sopkathonserverandroid1.domain.mission.repository.MissionBasketRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HypeService {

    private static final Long CURRENT_USER_ID = 1L;

    private final HypeRepository hypeRepository;
    private final MissionBasketRepository missionBasketRepository;

    @Transactional
    public HypeToggleResponse toggleHype(Long missionBasketId) {
        MissionBasket missionBasket = missionBasketRepository
                .findByIdAndUserId(missionBasketId, CURRENT_USER_ID)
                .orElseThrow(MissionBasketNotFoundException::new);

        boolean isHyped = hypeRepository.findByMissionBasketId(missionBasketId)
                .map(hype -> {
                    hype.toggleRecommendation();
                    return hype.isRecommended();
                })
                .orElseGet(() -> {
                    hypeRepository.save(Hype.create(missionBasketId));
                    return true;
                });

        Long missionId = missionBasket.getMissionId();
        long hypeCount = hypeRepository.countRecommendedByMissionId(missionId);
        return HypeToggleResponse.of(missionBasketId, missionId, isHyped, hypeCount);
    }
}
