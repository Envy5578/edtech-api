package com.example.focus_group.auth.services;


import com.example.focus_group.mail.MailService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.focus_group.auth.tokens.JwtTokenService;

import lombok.RequiredArgsConstructor;


@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class PasswordResetService {
    private final JwtTokenService jwtTokenService;
    private final MailService mailService;
    public ResponseEntity<?> resetPasswordForEmailToken(String token) {
        if(jwtTokenService.validateToken(token)) {
            //TODO MAILSERVICE SEND TOKEN FOR EMAIL;
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/reset-Password");
            headers.add("Email", jwtTokenService.extractUserEmailFromJWT(token));
            System.out.println(headers.get("Location") + " " + headers.get("Email"));
            return new ResponseEntity<>(headers.get("Location"), HttpStatus.FOUND);
        }
        return ResponseEntity.badRequest().body("Invalid token");
    }
    //    private final UserRepository userRepository;
//    private final MailService mailService;
//
//    public void sendPasswordResetEmail(@NotNull ResetPasswordRequest request) {
//        UserEntity userEntity = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        MailInfo mailInfo = MailInfo.builder()
//                .recipientEmail(userEntity.getEmail())
//                .emailType(TokenType.RESET_PASSWORD_EMAIL)
//                .build();
//        mailService.sendEmail(mailInfo);
//    }

}

