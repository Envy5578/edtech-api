package com.example.focus_group.auth.tokens;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.focus_group.auth.entities.UserEntity;
import com.example.focus_group.auth.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final UserRepository userRepository;

    public UserEntity getUserFromRequest() {
        return userRepository.findById(Long.valueOf(((UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
