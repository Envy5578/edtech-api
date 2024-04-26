package com.example.focus_group.mail.models;


import com.example.focus_group.auth.enumType.TokenType;

import lombok.Data;

@Data
public class MailInfo {
    private String recipientEmail;
    private TokenType emailType;
    private String context;
}
