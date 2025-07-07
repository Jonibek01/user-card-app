package uz.app.lesson2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.lesson2.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
