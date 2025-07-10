package uz.app.lesson2.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.app.lesson2.controller.AuthController;
import uz.app.lesson2.dto.EmailAndPasswordDTO;
import uz.app.lesson2.dto.ResponseMessage;
import uz.app.lesson2.dto.UserRequestDTO;
import uz.app.lesson2.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    final AuthService authService;

    public ResponseEntity<?> signup(UserRequestDTO userRequestDTO) {
        var response = authService.signup(userRequestDTO);
        return ResponseEntity
                .status(response.getStatus()?200:400)
                .body(response);
    }

    public ResponseEntity<?> signin(EmailAndPasswordDTO emailAndPasswordDTO) {
        var response = authService.signin(emailAndPasswordDTO);
        return ResponseEntity
                .status(response.getStatus()?200:400)
                .body(response);
    }

    public ResponseEntity<?> confirm(String code, String email) {
        var response = authService.confirm(code, email);
        return ResponseEntity
                .status(response.getStatus()?200:400)
                .body(response);
    }
}
