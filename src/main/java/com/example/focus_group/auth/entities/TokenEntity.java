package com.example.focus_group.auth.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@AllArgsConstructor
@Data
@Table(name = "tokens")
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "refresh_token")
    private String refreshToken;

//    public static TokenEntity build(TokenEntity token) {
//        return new TokenEntity(
//                token.getId(),
//                token.getResetToken(),
//                token.getRefreshToken()
//        );
//    }
}
