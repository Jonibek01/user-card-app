package uz.app.lesson2.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String location;
    private Double balance;
}
