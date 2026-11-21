package sopt._39sopkathonserverandroid1.domain.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sopt._39sopkathonserverandroid1.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            select u as user,
                   (select count(basket)
                    from MissionBasket basket
                    where basket.userId = u.id and basket.isCompleted = true) as completedMissionCount,
                   (select count(reaction)
                    from Like reaction
                    join reaction.review review
                    join review.missionBasket reviewedBasket
                    where reviewedBasket.userId = u.id) as receivedLikeCount
            from User u
            where u.id = :userId
            """)
    Optional<UserDetailProjection> findDetailById(@Param("userId") Long userId);

    interface UserDetailProjection {

        User getUser();

        long getCompletedMissionCount();

        long getReceivedLikeCount();
    }
}
