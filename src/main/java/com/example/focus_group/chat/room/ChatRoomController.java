package com.example.focus_group.chat.room;

import com.example.focus_group.chat.room.dtos.AddChatRoomMembersDTO;
import com.example.focus_group.chat.room.dtos.CreateChatRoomDTO;
import com.example.focus_group.chat.room.dtos.RemoveChatRoomMembersDTO;
import com.example.focus_group.chat.room.dtos.UpdateChatRoomDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/chatroom")
@RequiredArgsConstructor
@Tag(name = "Chat-room API")
public class ChatRoomController {

    private final ChatRoomService service;

    @Operation(summary = "Get user's chats")
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<ChatRoom>> getChatsByUserId(@PathVariable Integer userId) {
        var rooms = service.getRoomsByMemberId(userId);

        return ResponseEntity.ok(rooms);
    }

    @Operation(summary = "Get chat by its id")
    @GetMapping("{id}")
    public ResponseEntity<ChatRoom> getChatById(@PathVariable Long id) {
        try {
            var room = service.getRoomById(id);

            return ResponseEntity.ok(room);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }

    }

    @Operation(summary = "Create chat")
    @PostMapping
    public ResponseEntity<ChatRoom> createChat(@RequestBody CreateChatRoomDTO creating) {
        try {
            return ResponseEntity.ok(service.createRoom(
                    creating.getUsers(),
                    creating.getName(),
                    creating.getRoomType()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Update chat")
    @PutMapping
    public ResponseEntity<ChatRoom> updateChat(@RequestBody UpdateChatRoomDTO updating) {
        try {
            return ResponseEntity.ok(service.updateRoom(updating));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Add new members to chat")
    @PutMapping("/add-members")
    public ResponseEntity<ChatRoom> addMembers(@RequestBody AddChatRoomMembersDTO adding) {
        try {
            return ResponseEntity.ok(service.addMembers(adding));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Remove existing members from chat")
    @PutMapping("/remove-members")
    public ResponseEntity<ChatRoom> removeMembers(@RequestBody RemoveChatRoomMembersDTO removing) {
        try {
            return ResponseEntity.ok(service.removeMembers(removing));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete chat by it's id")
    @DeleteMapping("/{id}")
    public ResponseEntity removeChat(@PathVariable Long id) {
        service.removeChat(id);
        return ResponseEntity.noContent().build();
    }
}
