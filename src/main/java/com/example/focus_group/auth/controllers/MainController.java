package com.example.focus_group.auth.controllers;
import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.focus_group.auth.dto.NewPasswordRequest;
import com.example.focus_group.auth.services.UserService;

import lombok.RequiredArgsConstructor;


@RequestMapping("/secured")
@RestController
@RequiredArgsConstructor
public class MainController {
    private final UserService service;

    @GetMapping("/user")
    public ResponseEntity<?> getUser() {
        return ResponseEntity.ok(service.getMe());
    }
    
    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody NewPasswordRequest request, Principal connectedUser) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
    
}
