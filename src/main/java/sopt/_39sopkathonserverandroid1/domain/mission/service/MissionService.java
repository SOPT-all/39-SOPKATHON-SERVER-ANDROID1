package sopt._39sopkathonserverandroid1.domain.mission.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionDetailResponse;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionListResponse;
import sopt._39sopkathonserverandroid1.domain.mission.entity.Mission;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionNotFoundException;
import sopt._39sopkathonserverandroid1.domain.mission.repository.MissionRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;

    public List<MissionListResponse> findMissions() {
        return missionRepository.findAllOrderByHypeCountDesc().stream()
                .map(mission -> MissionListResponse.of(
                        mission.getMissionId(),
                        mission.getImageUrl(),
                        mission.getTitle(),
                        mission.getHypeCount()
                ))
                .toList();
    }

    public MissionDetailResponse getMissionDetail(Long missionId) {

        Mission mission = missionRepository.findDetailById(missionId)
                .orElseThrow(MissionNotFoundException::new);

        return MissionDetailResponse.from(mission);
    }
}
