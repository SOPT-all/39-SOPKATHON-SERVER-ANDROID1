package sopt._39sopkathonserverandroid1.domain.hype.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import sopt._39sopkathonserverandroid1.domain.mission.entity.MissionBasket;
import sopt._39sopkathonserverandroid1.global.entity.BaseTimeEntity;

@Entity
@Getter
@Table(
        name = "hypes",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_hypes_mission_basket",
                columnNames = "mission_basket_id"
        )
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hype extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mission_basket_id", nullable = false)
    private Long missionBasketId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_basket_id", insertable = false, updatable = false)
    private MissionBasket missionBasket;

    @ColumnDefault("false")
    @Column(name = "is_recommended", nullable = false)
    private boolean isRecommended;
}
