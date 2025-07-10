package uz.app.lesson2.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.app.lesson2.controller.CardController;
import uz.app.lesson2.dto.CardRequestDTO;
import uz.app.lesson2.dto.ResponseMessage;
import uz.app.lesson2.dto.TransactionRequestDTO;
import uz.app.lesson2.entity.Card;
import uz.app.lesson2.entity.User;
import uz.app.lesson2.service.CardService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class CardControllerImpl implements CardController {
    final CardService cardService;

    public ResponseEntity<?> getAllCards() {
        return ResponseEntity.ok(cardService.getAllCards());
    }

    public ResponseEntity<?> createCard(CardRequestDTO cardRequestDTO) {
        var response = cardService.createCard(cardRequestDTO.getCard());
        if (!response.getStatus()) {
            if (response.getMessage().contains("User not found")) {
                return ResponseEntity.status(404).body(response);
            }
            if (response.getMessage().contains("already exists")) {
                return ResponseEntity.status(409).body(response);
            }
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.status(201).body(response); // 201 Created
    }

    public ResponseEntity<?> editCard(Long id, CardRequestDTO cardRequestDTO) {
        var response = cardService.editCard(id, cardRequestDTO.getCard());

        if (!response.getStatus()) {
            return ResponseEntity.status(400).body(response);
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> deleteCard(Long id) {
        var response = cardService.deleteCard(id);
        if (!response.getStatus()) {
            return ResponseEntity.status(400).body(response);
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> transfer(TransactionRequestDTO dto, Principal principal) {
        ResponseMessage response = cardService.transfer(dto, principal);
        if (!response.getStatus()) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<?> getAllMyCards() {
        return ResponseEntity.ok(cardService.getAllMyCards());
    }
}
