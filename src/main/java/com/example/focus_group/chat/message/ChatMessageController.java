package com.example.focus_group.chat.message;

import com.example.focus_group.chat.message.dtos.ChatMessageDTO;
import com.example.focus_group.chat.message.dtos.CreateMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat-message")
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService service;

    @GetMapping("/by-chatroom/{chatRoomId}")
    public ResponseEntity<List<ChatMessageDTO>> getMessagesByChatRoom(@PathVariable Long chatRoomId) {
        var rooms = service.getMessagesByChatId(chatRoomId);

        return ResponseEntity.ok(rooms);
    }

    @PostMapping
    public ResponseEntity<ChatMessageDTO> createChat(@RequestBody CreateMessageDTO creating) {
        try {
            return ResponseEntity.ok(service.createMessage(creating));
        }
        catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
