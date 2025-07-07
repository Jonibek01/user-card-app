package uz.app.lesson2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.app.lesson2.dto.CardRequestDTO;
import uz.app.lesson2.entity.Card;
import uz.app.lesson2.entity.User;

@RequestMapping("/card")
public interface CardController {
    @GetMapping
    ResponseEntity<?> getAllCards();
    @PostMapping
    ResponseEntity<?> createCard(@RequestBody CardRequestDTO cardRequestDTO);
    @PutMapping("/{id}")
    ResponseEntity<?> editCard(@PathVariable Long id, @RequestBody CardRequestDTO cardRequestDTO);
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCard(@PathVariable Long id);
}
