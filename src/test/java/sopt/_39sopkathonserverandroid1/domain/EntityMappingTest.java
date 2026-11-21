package sopt._39sopkathonserverandroid1.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import sopt._39sopkathonserverandroid1.domain.hype.entity.Hype;
import sopt._39sopkathonserverandroid1.domain.like.entity.Like;
import sopt._39sopkathonserverandroid1.domain.mission.entity.Mission;
import sopt._39sopkathonserverandroid1.domain.mission.entity.MissionBasket;
import sopt._39sopkathonserverandroid1.domain.review.entity.Review;
import sopt._39sopkathonserverandroid1.domain.user.entity.User;
import sopt._39sopkathonserverandroid1.domain.user.enums.Grade;
import sopt._39sopkathonserverandroid1.global.entity.BaseTimeEntity;

class EntityMappingTest {

    @Test
    void mapsAllEntities() {
        List<Class<?>> entities = List.of(
                User.class,
                Mission.class,
                MissionBasket.class,
                Review.class,
                Like.class,
                Hype.class
        );

        assertTrue(entities.stream().allMatch(type -> type.isAnnotationPresent(Entity.class)));
    }

    @Test
    void validatesHibernateMetadata() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .build();

        try {
            try (SessionFactory sessionFactory = new MetadataSources(registry)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Mission.class)
                    .addAnnotatedClass(MissionBasket.class)
                    .addAnnotatedClass(Review.class)
                    .addAnnotatedClass(Like.class)
                    .addAnnotatedClass(Hype.class)
                    .buildMetadata()
                    .buildSessionFactory()) {
                assertNotNull(sessionFactory.getMetamodel());
            }
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    @Test
    void keepsOnlyCreatedAtInBaseTimeEntity() {
        assertEquals(
                List.of("createdAt"),
                Arrays.stream(BaseTimeEntity.class.getDeclaredFields())
                        .map(field -> field.getName())
                        .sorted()
                        .toList()
        );
    }

    @Test
    void mapsGradeAsString() throws NoSuchFieldException {
        Enumerated enumerated = User.class.getDeclaredField("badge").getAnnotation(Enumerated.class);

        assertNotNull(enumerated);
        assertEquals(EnumType.STRING, enumerated.value());
        assertEquals(List.of("CHALLENGER", "THIRD", "SECOND", "FIRST"),
                Arrays.stream(Grade.values()).map(Enum::name).toList());
    }

    @Test
    void completesMissionBasketWithTimestamp() throws Exception {
        Constructor<MissionBasket> constructor = MissionBasket.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        MissionBasket missionBasket = constructor.newInstance();

        assertFalse(missionBasket.isCompleted());

        missionBasket.complete();

        assertTrue(missionBasket.isCompleted());
        assertNotNull(missionBasket.getCompletedAt());
    }
}
