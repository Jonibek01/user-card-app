package uz.app.lesson2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.app.lesson2.dto.CardRequestDTO;
import uz.app.lesson2.dto.TransactionRequestDTO;
import uz.app.lesson2.entity.Card;
import uz.app.lesson2.entity.User;

import java.security.Principal;

@RequestMapping("/card")
public interface CardController {
    @GetMapping
    ResponseEntity<?> getAllCards();
    @GetMapping("/my")
    ResponseEntity<?> getAllMyCards();
    @PostMapping
    ResponseEntity<?> createCard(@RequestBody CardRequestDTO cardRequestDTO);
    @PutMapping("/{id}")
    ResponseEntity<?> editCard(@PathVariable Long id, @RequestBody CardRequestDTO cardRequestDTO);
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCard(@PathVariable Long id);
    @PostMapping("/transfer")
    ResponseEntity<?> transfer(@RequestBody TransactionRequestDTO dto, Principal principal);

}
