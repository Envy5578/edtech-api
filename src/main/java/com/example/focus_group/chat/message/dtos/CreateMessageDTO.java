package com.example.focus_group.chat.message.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMessageDTO {
    private Long chatRoomId;
    private Integer senderId;
    private String content;
}
