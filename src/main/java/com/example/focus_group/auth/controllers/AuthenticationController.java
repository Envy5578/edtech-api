package com.example.focus_group.auth.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.focus_group.auth.dto.AuthenticationRequest;
import com.example.focus_group.auth.dto.AuthenticationResponse;
import com.example.focus_group.auth.dto.RegistrationRequest;
import com.example.focus_group.auth.dto.ResetPassword;
import com.example.focus_group.auth.services.AuthenticationService;
import com.example.focus_group.auth.services.PasswordResetService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication, Registration", description = "Authentication, Registration")
public class AuthenticationController {
    private final AuthenticationService service;
    private final PasswordResetService passwordResetService;
    @Operation(summary = "Registration")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid RegistrationRequest register) {
        service.register(register);
    }

    @Operation(summary = "Sign In")
    @PostMapping("/sign-in")
    public AuthenticationResponse signIn(@RequestBody @Valid AuthenticationRequest register, HttpServletRequest request) {
        return service.signIn(register, request);
    }


    //ПОЛУЧЕНИЕ ПАРОЛЯ С ССЫЛКИ НА ЕМЭЙЛ
    @Operation(summary = "Reset password for email")
    @GetMapping("/reset-password-email/{resetToken}")
    public ResponseEntity<?> resetPasswordForEmailToken(@PathVariable("resetToken") String resetToken) {
        return passwordResetService.resetPasswordForEmailToken(resetToken);
    }
    
    //СМЕНА ПАРОЛЯ С ССЫЛКИ НА ЕМЭЙЛ
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPassword password, HttpServletRequest request) {
        return service.resetPassword(password, request);
    }
}
