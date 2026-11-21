package sopt._39sopkathonserverandroid1.domain.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt._39sopkathonserverandroid1.domain.mission.dto.request.MissionCreateRequest;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionCreateResponse;
import sopt._39sopkathonserverandroid1.domain.mission.entity.Mission;
import sopt._39sopkathonserverandroid1.domain.mission.repository.MissionRepository;
import sopt._39sopkathonserverandroid1.domain.user.exception.UserNotFoundException;
import sopt._39sopkathonserverandroid1.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;
    private final UserRepository userRepository;

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
}
