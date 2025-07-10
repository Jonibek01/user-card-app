package uz.app.lesson2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.app.lesson2.dto.CardRequestDTO;
import uz.app.lesson2.dto.ResponseMessage;
import uz.app.lesson2.dto.TransactionRequestDTO;
import uz.app.lesson2.entity.Card;
import uz.app.lesson2.entity.User;
import uz.app.lesson2.repository.AuthRepository;
import uz.app.lesson2.repository.CardRepository;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {
    final CardRepository cardRepository;
    private final AuthRepository userRepository;

    public ResponseMessage getAllCards() {
        List<Card> all = cardRepository.findAll();
        return ResponseMessage.builder().status(true).data(all).message("card list").build();
    }

    public ResponseMessage getAllMyCards() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Card> allByUserId = cardRepository.findAllByUser_Id(principal.getId());
        return ResponseMessage
                .builder()
                .message("Cards found successfully")
                .status(true)
                .data(allByUserId)
                .build();
    }

    public ResponseMessage createCard(Card card) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (card == null) {
            return ResponseMessage.builder()
                    .status(false)
                    .data(null)
                    .message("Card data is missing")
                    .build();
        }

        boolean exists = cardRepository.existsByUserIdAndNumber(principal.getId(), card.getNumber());
        if (exists) {
            return ResponseMessage.builder()
                    .status(false)
                    .data(null)
                    .message("Card already exists for this user")
                    .build();
        }


        card.setUser(principal);
        cardRepository.save(card);
        return ResponseMessage.builder().status(true).data(card).message("card created successfully").build();
    }

    public ResponseMessage editCard(Long cardId, Card updatedCardData) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Card> optionalCard = cardRepository.findById(cardId);

        if (optionalCard.isEmpty()) {
            return ResponseMessage.builder()
                    .status(false)
                    .data(null)
                    .message("Card not found with id: " + cardId)
                    .build();
        }

        Card card = optionalCard.get();

        if (!card.getUser().getId().equals(principal.getId())) {
            return ResponseMessage.builder()
                    .status(false)
                    .message("You are not authorized to edit this card")
                    .build();
        }

        card.setPassword(updatedCardData.getPassword());
        card.setBalance(updatedCardData.getBalance());
        cardRepository.save(card);
        return ResponseMessage.builder().status(true).data(card).message("card edited").build();
    }

    public ResponseMessage deleteCard(Long id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (optionalCard.isEmpty()) {
            return ResponseMessage.builder().status(false).data(null).message("Card not found with id: " + id).build();
        }
        cardRepository.delete(optionalCard.get());
        return ResponseMessage.error("Card not found");
    }

    public ResponseMessage transfer(TransactionRequestDTO dto, Principal principal) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.err.println(user.getId());

        Card from = cardRepository.findByNumber(dto.getFromCardNumber()).orElse(null);

        if (from == null || !from.getUser().getId().equals(user.getId())) {
            return ResponseMessage.error("You can only transfer from your own card");
        }

        Card to = cardRepository.findByNumber(dto.getToCardNumber()).orElse(null);
        if (to == null) {
            return ResponseMessage.builder().status(false).data(null).message("Card not found").build();
        }

        if (from.getBalance() < dto.getAmount()) {
            return ResponseMessage.error("Insufficient balance");
        }

        if (!from.getPassword().equals(dto.getSenderCode())) {
            return ResponseMessage.error("Invalid pincode of card");
        }

        double transferAmount = dto.getAmount();
        double totalDeducted = transferAmount;

        if (!from.getCardType().equals(to.getCardType())) {
            double fee = transferAmount * 0.01;
            totalDeducted += fee;
        }

        if (from.getBalance() < totalDeducted)
            return ResponseMessage.error("Insufficient balance with fee");

        from.setBalance(from.getBalance() - totalDeducted);
        to.setBalance(to.getBalance() + transferAmount);

        cardRepository.save(from);
        cardRepository.save(to);

        return ResponseMessage.success("Transaction successful");
    }

}
