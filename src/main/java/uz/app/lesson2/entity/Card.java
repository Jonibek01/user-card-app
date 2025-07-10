package uz.app.lesson2.entity;

import jakarta.persistence.*;
import lombok.Data;
import uz.app.lesson2.entity.enums.CardType;

@Data
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Integer number;
    private String password;
    private String expireDate;
    @Enumerated(EnumType.STRING)
    private CardType cardType;
    private Double balance;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
