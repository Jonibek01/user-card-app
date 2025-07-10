package uz.app.lesson2.dto;

import lombok.Data;

@Data
public class TransactionRequestDTO {
    private Integer fromCardNumber;
    private String senderCode;
    private Integer toCardNumber;
    private Double amount;
}
