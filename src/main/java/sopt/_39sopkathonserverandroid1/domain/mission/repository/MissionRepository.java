package sopt._39sopkathonserverandroid1.domain.mission.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sopt._39sopkathonserverandroid1.domain.mission.entity.Mission;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query(value = """
            SELECT
                m.id AS missionId,
                m.image_url AS imageUrl,
                m.title AS title,
                COUNT(h.id) AS hypeCount
            FROM missions m
            LEFT JOIN mission_basket mb ON mb.mission_id = m.id
            LEFT JOIN hypes h
                ON h.mission_basket_id = mb.id
                AND h.is_recommended = TRUE
            GROUP BY m.id, m.image_url, m.title
            ORDER BY hypeCount DESC, m.id DESC
            """, nativeQuery = true)
    List<MissionListProjection> findAllOrderByHypeCountDesc();

    @Query("""
        SELECT m
        FROM Mission m
        JOIN FETCH m.user
        WHERE m.id = :missionId
    """)
    Optional<Mission> findDetailById(
            @Param("missionId") Long missionId
    );

    interface MissionListProjection {

        Long getMissionId();

        String getImageUrl();

        String getTitle();

        Long getHypeCount();
    }
}
