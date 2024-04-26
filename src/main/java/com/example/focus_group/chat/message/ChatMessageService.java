package com.example.focus_group.chat.message;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.focus_group.chat.message.dtos.ChatMessageDTO;
import com.example.focus_group.chat.message.dtos.CreateMessageDTO;
import com.example.focus_group.chat.room.ChatRoomRepository;
import com.example.focus_group.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public List<ChatMessageDTO> getMessagesByChatId(Long chatId) {
        var messages = chatMessageRepository.findByChatRoomId(chatId);
        return messages.stream()
                .map(m -> ChatMessageDTO.builder()
                        .id(m.getId())
                        .chatRoomId(m.getRoom().getId())
                        .senderId(m.getSender().getId())
                        .senderName(m.getSender().getFirstname() + " " + m.getSender().getLastname())
                        .content(m.getContent())
                        .sendTime(m.getTimestamp())
                        .build())
                .toList();
    }

    public ChatMessageDTO createMessage(CreateMessageDTO creating) {
        var chatRoom = chatRoomRepository.findById(creating.getChatRoomId())
                .orElseThrow();

        var sender = userRepository.findById(creating.getSenderId())
                .orElseThrow();

        var message = ChatMessage.builder()
                .room(chatRoom)
                .sender(sender)
                .content(creating.getContent())
                .timestamp(new Date())
                .build();

        var saved = chatMessageRepository.save(message);
        return ChatMessageDTO.builder()
                .id(saved.getId())
                .chatRoomId(saved.getRoom().getId())
                .senderId(saved.getSender().getId())
                .senderName(saved.getSender().getFirstname() + " " + saved.getSender().getLastname())
                .content(saved.getContent())
                .sendTime(saved.getTimestamp())
                .build();
    }
}
