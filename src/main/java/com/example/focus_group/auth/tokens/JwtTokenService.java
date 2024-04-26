package com.example.focus_group.auth.tokens;



import org.springframework.stereotype.Service;

import com.example.focus_group.auth.enumType.TokenType;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class JwtTokenService {
    private final JwtCore jwtCore;


    public String extractUserEmailFromJWT(String jwt) {
        return jwtCore.getUserEmailFromJwt(jwt);
    }


    public boolean validateToken(String jwt) {
        return jwtCore.validateToken(jwt);
    }

    public String generateResetToken(String email, TokenType tokenType) {
        return jwtCore.generateResetToken(email, tokenType);
    }
    public String generateAccessToken(String email, TokenType tokenType) {
        return jwtCore.generateAccessToken(email, tokenType);
    }
    public String generateRefreshToken(String email, TokenType tokenType) {
        return jwtCore.generateRefreshToken(email, tokenType);
    }
}

