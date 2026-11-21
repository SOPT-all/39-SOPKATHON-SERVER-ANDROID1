package sopt._39sopkathonserverandroid1.domain.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt._39sopkathonserverandroid1.domain.image.service.MissionImageUrlService;
import sopt._39sopkathonserverandroid1.domain.mission.dto.request.MissionCreateRequest;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionCreateResponse;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionDetailResponse;
import sopt._39sopkathonserverandroid1.domain.mission.entity.Mission;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionNotFoundException;
import sopt._39sopkathonserverandroid1.domain.mission.repository.MissionRepository;
import sopt._39sopkathonserverandroid1.domain.user.exception.UserNotFoundException;
import sopt._39sopkathonserverandroid1.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;
    private final UserRepository userRepository;
    private final MissionImageUrlService missionImageUrlService;

    @Transactional
    public MissionCreateResponse createMission(Long userId, MissionCreateRequest request) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }

        Mission mission = Mission.create(
                userId, request.title(), request.description(), request.instructions(), request.imageKey()
        );
        return MissionCreateResponse.of(missionRepository.save(mission));
    }

    public MissionDetailResponse getMissionDetail(Long missionId) {
        Mission mission = missionRepository.findDetailById(missionId)
                .orElseThrow(MissionNotFoundException::new);

        return MissionDetailResponse.from(mission, missionImageUrlService.resolve(mission.getImageUrl()));
    }
}
