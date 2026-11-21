package sopt._39sopkathonserverandroid1.domain.mission.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sopt._39sopkathonserverandroid1.domain.mission.entity.Mission;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    @Query("""
        SELECT m
        FROM Mission m
        JOIN FETCH m.user
        WHERE m.id = :missionId
    """)
    Optional<Mission> findDetailById(
            @Param("missionId") Long missionId
    );
}
