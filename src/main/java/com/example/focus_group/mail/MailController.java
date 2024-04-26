package com.example.focus_group.mail;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class MailController {

    private final MailService emailService;

    @GetMapping(value = "/simple-email/{user-email}")
    public @ResponseBody // возвращаемое значение должно быть сериализовано непосредственно в тело HTTP ответа.
    ResponseEntity<String> sendSimpleEmail(@PathVariable("user-email") String email) {
        return emailService.sendEmail(email);
    }
}
