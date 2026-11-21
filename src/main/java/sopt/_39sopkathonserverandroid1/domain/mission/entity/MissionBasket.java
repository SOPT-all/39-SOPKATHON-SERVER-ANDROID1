package sopt._39sopkathonserverandroid1.domain.mission.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import sopt._39sopkathonserverandroid1.domain.user.entity.User;
import sopt._39sopkathonserverandroid1.global.entity.BaseTimeEntity;

@Entity
@Getter
@Table(
        name = "mission_basket",
        uniqueConstraints = @UniqueConstraint(
                name = MissionBasket.USER_MISSION_UNIQUE_CONSTRAINT,
                columnNames = {"user_id", "mission_id"}
        )
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionBasket extends BaseTimeEntity {

    public static final String USER_MISSION_UNIQUE_CONSTRAINT = "uk_mission_basket_user_mission";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "mission_id", nullable = false)
    private Long missionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", insertable = false, updatable = false)
    private Mission mission;

    @ColumnDefault("false")
    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    public static MissionBasket create(Long userId, Long missionId) {
        MissionBasket basket = new MissionBasket();
        basket.userId = userId;
        basket.missionId = missionId;
        return basket;
    }

    public void complete() {
        this.isCompleted = true;
        this.completedAt = LocalDateTime.now();
    }
}
