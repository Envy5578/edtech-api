package com.example.focus_group.chat.message;

import java.util.Date;

import com.example.focus_group.chat.room.ChatRoom;
import com.example.focus_group.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private ChatRoom room;
    @ManyToOne
    private User sender;
    private String content;
    private Date timestamp;
}