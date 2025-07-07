package uz.app.lesson2.service;

import org.springframework.stereotype.Service;
import uz.app.lesson2.dto.ResponseMessage;
import uz.app.lesson2.entity.User;
import uz.app.lesson2.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseMessage getAllUsers(){
        List<User> all = userRepository.findAll();
        return ResponseMessage.builder().status(true).data(all).message("user list").build();
    }

    public ResponseMessage createUser(User user){
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseMessage.builder()
                    .status(false)
                    .data(null)
                    .message("User already exists with this email")
                    .build();
        }
        userRepository.save(user);
        return ResponseMessage.builder().status(true).data(user).message("user created").build();
    }

    public ResponseMessage editUser(Long id, User user){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            return ResponseMessage.builder().status(false).data(null).message("user not found").build();
        }
        User user1 = optionalUser.get();
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        userRepository.save(user1);
        return ResponseMessage.builder().status(true).data(user1).message("user edited").build();
    }

    public ResponseMessage deleteUser(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            return ResponseMessage.builder().status(false).data(null).message("user not found").build();
        }
        User user = optionalUser.get();
        userRepository.delete(user);
        return ResponseMessage.builder().status(true).data(null).message("deleted").build();
    }
}
