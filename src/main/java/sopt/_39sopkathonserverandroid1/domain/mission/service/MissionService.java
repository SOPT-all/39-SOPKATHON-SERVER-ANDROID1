package sopt._39sopkathonserverandroid1.domain.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionDetailResponse;
import sopt._39sopkathonserverandroid1.domain.mission.entity.Mission;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionNotFoundException;
import sopt._39sopkathonserverandroid1.domain.mission.repository.MissionRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;

    public MissionDetailResponse getMissionDetail(Long missionId) {

        Mission mission = missionRepository.findDetailById(missionId)
                .orElseThrow(MissionNotFoundException::new);

        return MissionDetailResponse.from(mission);
    }
}
