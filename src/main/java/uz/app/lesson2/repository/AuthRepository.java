package uz.app.lesson2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.lesson2.entity.User;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Long> {

    boolean existsByEmailAndPassword(String email, String password);
    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

    boolean existsByEmailAndConfCode(String email, String confCode);

}