package sopt._39sopkathonserverandroid1.domain.mission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt._39sopkathonserverandroid1.domain.mission.entity.Mission;

public interface MissionRepository extends JpaRepository<Mission, Long> {
}
