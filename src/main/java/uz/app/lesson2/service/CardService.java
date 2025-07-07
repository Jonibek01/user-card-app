package uz.app.lesson2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.app.lesson2.dto.CardRequestDTO;
import uz.app.lesson2.dto.ResponseMessage;
import uz.app.lesson2.entity.Card;
import uz.app.lesson2.entity.User;
import uz.app.lesson2.repository.CardRepository;
import uz.app.lesson2.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {
    final CardRepository cardRepository;
    private final UserRepository userRepository;

    public ResponseMessage getAllCards() {
        List<Card> all = cardRepository.findAll();
        return ResponseMessage.builder().status(true).data(all).message("card list").build();
    }

    public ResponseMessage createCard(Long userId, Card card) {
        if (card == null) {
            return ResponseMessage.builder()
                    .status(false)
                    .data(null)
                    .message("Card data is missing")
                    .build();
        }

        Optional<User> byId = userRepository.findById(userId);
        if(byId.isEmpty()){
            return ResponseMessage.builder().status(false).data(null).message("User not found with id: " + userId).build();
        }

        boolean b = cardRepository.existsByUserIdAndNumber(userId, card.getNumber());
        if(b){
            return ResponseMessage.builder().status(false).data(null).message("Card already exists for this User").build();

        }

        card.setUser(byId.get());
        cardRepository.save(card);
        return ResponseMessage.builder().status(true).data(card).message("card created").build();
    }

    public ResponseMessage editCard(Long id, Card card) {
        Optional<Card> optionalCard = cardRepository.findById(id);

        if (optionalCard.isEmpty()) {
            return ResponseMessage.builder()
                    .status(false)
                    .data(null)
                    .message("Card not found with id: " + id)
                    .build();
        }
        Card card1 = optionalCard.get();
        card1.setNumber(card.getNumber());
        card1.setPassword(card.getPassword());
        card1.setExpireDate(card.getExpireDate());
        cardRepository.save(card1);
        return ResponseMessage.builder().status(true).data(card1).message("card edited").build();
    }

    public ResponseMessage deleteCard(Long id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (optionalCard.isEmpty()){
            return ResponseMessage.builder().status(false).data(null).message("Card not found with id: " + id).build();
        }
        cardRepository.delete(optionalCard.get());
        return ResponseMessage.builder().status(true).data(null).message("successfully deleted").build();
    }
}
