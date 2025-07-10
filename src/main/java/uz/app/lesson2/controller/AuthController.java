package uz.app.lesson2.controller;

import org.hibernate.sql.ast.tree.from.StandardTableGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import uz.app.lesson2.dto.EmailAndPasswordDTO;
import uz.app.lesson2.dto.UserRequestDTO;

@RequestMapping("/auth")
public interface AuthController {

    @PostMapping("/signup")
    ResponseEntity<?> signup(@RequestBody UserRequestDTO userRequestDTO);

    @PostMapping("/signin")
    ResponseEntity<?> signin(@RequestBody EmailAndPasswordDTO emailAndPasswordDTO);

    @GetMapping("/confirm")
    ResponseEntity<?> confirm(@RequestParam String code, @RequestParam String email);
}
