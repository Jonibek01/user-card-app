package uz.app.lesson2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.lesson2.entity.Card;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByUserIdAndNumber(Long userId, Integer number);
    List<Card> findAllByUser_Id(Long userId);

    Optional<Card> findByNumber(Integer number);
}
