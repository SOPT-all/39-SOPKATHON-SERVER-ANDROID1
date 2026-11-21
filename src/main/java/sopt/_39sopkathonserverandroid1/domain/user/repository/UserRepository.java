package sopt._39sopkathonserverandroid1.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt._39sopkathonserverandroid1.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
