package com.example.focus_group.auth.services;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.focus_group.auth.dto.AuthenticationRequest;
import com.example.focus_group.auth.dto.AuthenticationResponse;
import com.example.focus_group.auth.dto.RegistrationRequest;
import com.example.focus_group.auth.dto.ResetPassword;
import com.example.focus_group.auth.entities.UserEntity;
import com.example.focus_group.auth.enumType.ErrorCode;
import com.example.focus_group.auth.enumType.TokenType;
import com.example.focus_group.auth.exceptions.BadRequestException;
import com.example.focus_group.auth.tokens.JwtTokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AuthenticationService {
    public static final int ACCESS_TOKEN_EXPIRES_IN_MINUTES = 30;
    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    public void register(RegistrationRequest register) {
        userService.save(register);
        // sendMailVerification(register); ОТПРАВКА ЛОКАЛЬНОГО ACTIVE ТОКЕНА TODO
    }

    private void sendMailVerification(RegistrationRequest register) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendMailVerification'");
    }

    public AuthenticationResponse signIn(@Valid AuthenticationRequest register, HttpServletRequest request) {
        final Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(register.email(), register.password()));
        } catch (BadCredentialsException e) {
            throw new BadRequestException(ErrorCode.INVALID_CREDENTIALS);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtTokenService.generateAccessToken(register.email(), TokenType.ACCESS_TOKEN);
        String refreshToken = jwtTokenService.generateRefreshToken(register.email(), TokenType.REFRESH_TOKEN);

        return new AuthenticationResponse(TokenType.REFRESH_TOKEN.name() , accessToken, ACCESS_TOKEN_EXPIRES_IN_MINUTES, refreshToken);

    }



    public ResponseEntity<?> resetPassword(@Valid ResetPassword password , HttpServletRequest request) {
        String token = request.getParameter("token");
        UserEntity user = null;
        if(jwtTokenService.validateToken(token)) {
            user = userService.findByEmail(jwtTokenService.extractUserEmailFromJWT(token)).orElseThrow();
        }
        userService.resetPassword(password, user);
        // TODO СМЕНА ПАРОЛЯ С ССЫЛКИ НА ЕМЭЙЛ
        
        return ResponseEntity.ok("Password changed successfully");
    }
}
