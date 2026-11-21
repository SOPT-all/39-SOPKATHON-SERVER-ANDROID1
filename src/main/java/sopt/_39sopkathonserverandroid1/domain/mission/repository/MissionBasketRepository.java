package sopt._39sopkathonserverandroid1.domain.mission.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sopt._39sopkathonserverandroid1.domain.mission.entity.MissionBasket;

public interface MissionBasketRepository extends JpaRepository<MissionBasket, Long> {

    boolean existsByUserIdAndMissionId(Long userId, Long missionId);

    Optional<MissionBasket> findByIdAndUserId(Long id, Long userId);

    @Query("""
            select basket
            from MissionBasket basket
            join fetch basket.mission mission
            where basket.userId = :userId
              and basket.completedAt is null
            order by mission.createdAt desc, mission.id desc
            """)
    List<MissionBasket> findIncompleteByUserId(@Param("userId") Long userId);

    @Query("""
            select basket
            from MissionBasket basket
            join fetch basket.mission mission
            where basket.userId = :userId
              and basket.completedAt is not null
            order by mission.createdAt desc, mission.id desc
            """)
    List<MissionBasket> findCompletedByUserId(@Param("userId") Long userId);
}
