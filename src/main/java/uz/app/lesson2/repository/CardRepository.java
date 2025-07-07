package uz.app.lesson2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.lesson2.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByUserIdAndNumber(Long userId, Integer number);
}
