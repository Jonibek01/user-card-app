package uz.app.lesson2.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Integer number;
    private String password;
    private String expireDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
