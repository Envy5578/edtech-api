package com.example.focus_group.auth.tokens;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.focus_group.auth.enumType.TokenType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtCore {

    @Value("${testing.app.secret}")
    private String secret;
    private static final String AUTHORIZATION = "Authorization";
    private static final String EMAIL = "email";
    private static final String TOKEN_TYPE = "tokenType";
    public String generateAccessToken(String email, TokenType tokenType) {

        return JWT.create()
                .withSubject(AUTHORIZATION)
                .withClaim(EMAIL, email)
                .withClaim(TOKEN_TYPE, tokenType.name())
                .withExpiresAt(new java.util.Date(System.currentTimeMillis() + tokenType.getTokenExpiration()))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    public String generateResetToken(String email, TokenType tokenType) {

        return JWT.create()
                .withSubject(AUTHORIZATION)
                .withClaim(EMAIL, email)
                .withClaim(TOKEN_TYPE, tokenType.name())
                .withExpiresAt(new java.util.Date(System.currentTimeMillis() + tokenType.getTokenExpiration()))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }
    public String generateRefreshToken(String email, TokenType tokenType) {
        
        return JWT.create()
                .withSubject(AUTHORIZATION)
                .withClaim(EMAIL, email)
                .withClaim(TOKEN_TYPE, tokenType.name())
                .withExpiresAt(new java.util.Date(System.currentTimeMillis() + tokenType.getTokenExpiration()))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    public String getUserEmailFromJwt(String jwt) {
        return JWT.require(Algorithm.HMAC512(secret.getBytes()))
                .build()
                .verify(jwt)
                .getClaim(EMAIL)
                .asString();
    }
    public boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512(secret.getBytes()))
            .withSubject(AUTHORIZATION)
            .build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
