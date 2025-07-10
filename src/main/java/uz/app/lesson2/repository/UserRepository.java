//package uz.app.lesson2.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.security.core.userdetails.UserDetails;
//import uz.app.lesson2.entity.User;
//
//import java.util.Optional;
//
//public interface UserRepository extends JpaRepository<User, Long> {
//    boolean existsByEmail(String email);
//
//    Optional<User> findByEmail(String email);
//}
