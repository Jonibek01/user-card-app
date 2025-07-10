package uz.app.lesson2.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.app.lesson2.dto.EmailAndPasswordDTO;
import uz.app.lesson2.dto.ResponseMessage;
import uz.app.lesson2.dto.TransactionRequestDTO;
import uz.app.lesson2.dto.UserRequestDTO;
import uz.app.lesson2.entity.User;
import uz.app.lesson2.entity.enums.Role;
import uz.app.lesson2.repository.AuthRepository;
import uz.app.lesson2.service.AuthService;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    final AuthRepository authRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;
//    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseMessage signup(UserRequestDTO userRequestDTO) {
        boolean byEmailAndPassword = authRepository.existsByEmailAndPassword(userRequestDTO.getEmail(), userRequestDTO.getPassword());
        if (byEmailAndPassword) {
            return ResponseMessage.builder().message("user already exist").status(false).data(null).build();
        }
        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setRole(Role.USER);
        String code = generateCode();
        user.setEnabled(false);
        user.setConfCode(code);
        authRepository.save(user);
        sendVerificationCode(userRequestDTO.getEmail(), code);
        return ResponseMessage.builder().message("signed up, code sent to user").data(userRequestDTO).status(true).build();
    }

    private void sendVerificationCode(String email, String code) {
        String subject = "Tasdiqlash kodingiz";
        String body = "Hurmatli foydalanuvchi,\n\nSizning tasdiqlash kodingiz: " + code + "\n\nIltimos, bu kodni hech kimga bermang. Yoki quyidagi link orqali tasdiqlang:\n" +
                "http://localhost:8080/auth/confirm?code=" + code + "&email=" + email + "\n\n" +
                "Hurmat bilan,\n" +
                "Sizning tizimingiz";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("tjonibek2001@gmail.com");
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        mailSender.send(mailMessage);
    }

    private String generateCode() {
        StringBuilder confCode = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            confCode.append(random.nextInt(9));
        }
        return confCode.toString();
    }

    @Override
    public ResponseMessage signin(EmailAndPasswordDTO emailAndPasswordDTO) {
//        Optional<User> byEmailAndPassword = authRepository.findByEmailAndPassword(emailAndPasswordDTO.getEmail(), emailAndPasswordDTO.getPassword());
        Optional<User> byEmail = authRepository.findByEmail(emailAndPasswordDTO.getEmail());


        if (byEmail.isEmpty()) {
            return ResponseMessage.builder().message("user not found").status(false).data(null).build();
        }
        User user = byEmail.get();
        if (passwordEncoder.matches(emailAndPasswordDTO.getPassword(), user.getPassword())) {
            if (!user.getEnabled()){
                return ResponseMessage.builder()
                        .data(null)
                        .status(false)
                        .message("Please confirm your email")
                        .build();
            }
            String s = new String(Base64.getEncoder().encode(user.getEmail().getBytes()));
            return ResponseMessage.builder()
                    .data(s)
                    .status(true)
                    .message("signed in successfully")
                    .build();
        }
        return ResponseMessage.builder()
                .data(null)
                .status(false)
                .message("Email or password is incorrect")
                .build();
    }


    @Override
    public ResponseMessage confirm(String code, String email) {
        User byEmail = authRepository.findByEmail(email).orElseThrow();
        boolean byEmailAndConfCode = authRepository.existsByEmailAndConfCode(email, code);

        if (byEmailAndConfCode) {
            if (byEmail.getEnabled()) {
                return ResponseMessage.builder().message("user already confirmed").status(false).data(email).build();
            }
            byEmail.setEnabled(true);
            authRepository.save(byEmail);
            return ResponseMessage.builder().message("user successfully confirmed").status(true).data(email).build();
        }
        return ResponseMessage.builder().message("user email or code is incorrect").status(false).data(email + "  \n " + code).build();
    }


}
