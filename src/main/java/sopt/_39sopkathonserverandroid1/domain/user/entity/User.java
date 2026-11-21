package sopt._39sopkathonserverandroid1.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import sopt._39sopkathonserverandroid1.domain.user.enums.AgeCategory;
import sopt._39sopkathonserverandroid1.domain.user.enums.Grade;
import sopt._39sopkathonserverandroid1.global.entity.BaseTimeEntity;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "age", nullable = false)
    private AgeCategory age;

    @ColumnDefault("'CHALLENGER'")
    @Enumerated(EnumType.STRING)
    @Column(name = "badge", nullable = false)
    private Grade badge = Grade.CHALLENGER;

    @Column(name = "profile_image_url")
    private String profileImageUrl;
}
