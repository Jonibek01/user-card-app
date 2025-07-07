package uz.app.lesson2.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.app.lesson2.controller.UserController;
import uz.app.lesson2.dto.ResponseMessage;
import uz.app.lesson2.entity.User;
import uz.app.lesson2.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    final UserService userService;

    public ResponseEntity<?> getAllUsers() {
       return ResponseEntity.ok(userService.getAllUsers());
    }


    public ResponseEntity<?> createUser(User user) {
        var response = userService.createUser(user);
        if (!response.getStatus()){
            if (response.getMessage().contains("User not found")) {
                return ResponseEntity.status(404).body(response);
            }
            if (response.getMessage().contains("already exists")) {
                return ResponseEntity.status(409).body(response);
            }
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<?> editUser(Long id, User user) {
        var response = userService.editUser(id,user);
        if(!response.getStatus()){
            return ResponseEntity.status(400).body(response);
        }
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<?> deleteUser(Long id) {
        var response = userService.deleteUser(id);
        if(!response.getStatus()){
            return ResponseEntity.status(400).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
