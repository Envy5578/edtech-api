package com.example.focus_group.chat.message.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDTO {
    private Long id;
    private Long chatRoomId;
    private Integer senderId;
    private String senderName;
    private String content;
    private Date sendTime;
}
