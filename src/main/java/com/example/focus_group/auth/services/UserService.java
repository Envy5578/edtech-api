package com.example.focus_group.auth.services;



import java.security.Principal;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.focus_group.auth.dto.NewPasswordRequest;
import com.example.focus_group.auth.dto.RegistrationRequest;
import com.example.focus_group.auth.dto.ResetPassword;
import com.example.focus_group.auth.dto.UserDTO;
import com.example.focus_group.auth.entities.UserEntity;
import com.example.focus_group.auth.enumType.ErrorCode;
import com.example.focus_group.auth.exceptions.BadRequestException;
import com.example.focus_group.auth.mapper.UserMapper;
import com.example.focus_group.auth.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public Optional<UserEntity> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @SuppressWarnings("null")
    public void save(RegistrationRequest register) {
        Optional<UserEntity> existingUser = repository.findByEmail(register.email());
        if (existingUser.isPresent()) {
            throw new BadRequestException(
                    ErrorCode.EMAIL_ALREADY_REGISTERED
            );
        }

        UserEntity user = UserEntity.builder()
                .firstName(register.firstName())
                .lastName(register.lastName())
                .email(register.email())
                .password(passwordEncoder.encode(register.password()))
                .active(false)
                .build();
        repository.save(user);
    }

    public UserDTO getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserEntity user = null;
        if (principal instanceof UserDetails) {
            user = (UserEntity) ((UserDetails) principal);
        }
        return UserMapper.toDTO(user);
    }

    public void changePassword(NewPasswordRequest request, Principal connectedUser) {
        UserEntity user = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if(!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
            
        }
        if(!request.password().equals(request.confirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }
        user.setPassword(passwordEncoder.encode(request.password()));
        repository.save(user);
    }
    public void resetPassword(ResetPassword request, UserEntity user) {
        if(!request.password().equals(request.confirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }
        user.setPassword(passwordEncoder.encode(request.password()));
        repository.save(user);
    }
    
}
