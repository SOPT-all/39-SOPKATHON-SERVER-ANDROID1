package sopt._39sopkathonserverandroid1.domain.hype.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sopt._39sopkathonserverandroid1.domain.hype.entity.Hype;

public interface HypeRepository extends JpaRepository<Hype, Long> {

    Optional<Hype> findByMissionBasketId(Long missionBasketId);

    @Query("""
            select count(hype)
            from Hype hype
            join hype.missionBasket basket
            where basket.missionId = :missionId
              and hype.isRecommended = true
            """)
    long countRecommendedByMissionId(@Param("missionId") Long missionId);
}
