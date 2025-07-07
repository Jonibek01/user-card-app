package uz.app.lesson2.dto;

import lombok.Data;
import uz.app.lesson2.entity.Card;

@Data
public class CardRequestDTO {
    private Long userId;
    private Card card;
}
