package uz.app.lesson2.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import uz.app.lesson2.dto.EmailAndPasswordDTO;
import uz.app.lesson2.dto.ResponseMessage;
import uz.app.lesson2.dto.UserRequestDTO;

public interface AuthService {
    ResponseMessage signup(UserRequestDTO userRequestDTO);
    ResponseMessage signin(EmailAndPasswordDTO emailAndPasswordDTO);
    ResponseMessage confirm(String code,  String email);
}
