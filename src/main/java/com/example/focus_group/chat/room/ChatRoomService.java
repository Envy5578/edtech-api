package com.example.focus_group.chat.room;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.focus_group.chat.room.dtos.AddChatRoomMembersDTO;
import com.example.focus_group.chat.room.dtos.RemoveChatRoomMembersDTO;
import com.example.focus_group.chat.room.dtos.UpdateChatRoomDTO;
import com.example.focus_group.user.UserRepository;
import com.example.focus_group.user.dtos.UserDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public List<ChatRoom> getRoomsByMemberEmail(String email) {
        var user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return chatRoomRepository.findByMembersContains(user.get());
        }

        return new ArrayList<>();
    }

    public List<ChatRoom> getRoomsByMemberId(Integer id) {
        var user = userRepository.findById(id);

        if (user.isPresent()) {
            return chatRoomRepository.findByMembersContains(user.get());
        }

        return new ArrayList<>();
    }

    public ChatRoom getRoomById(Long id) {
        var roomOpt = chatRoomRepository.findById(id);

        if (roomOpt.isPresent()) {
            return roomOpt.get();
        }

        throw new RuntimeException("Room with id = " + id + " not found");
    }


    public ChatRoom createRoom(
            List<UserDTO> users,
            String name,
            Type type
    ) {
        var members = users.stream()
                .map(u -> userRepository.findByEmail(u.getEmail()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        ChatRoom newRoom;

        if (type == Type.GROUP) {
            if (members.isEmpty()) {
                throw new RuntimeException("Empty group can not be created");
            }
        } else {
            if (members.size() != 2) {
                throw new RuntimeException("Personal chat should contain 2 members");
            }
        }

        newRoom = ChatRoom
                .builder()
                .name(name)
                .members(members)
                .type(type)
                .build();


        var savedRoom = chatRoomRepository.save(newRoom);
        return savedRoom;
    }

    public ChatRoom updateRoom(UpdateChatRoomDTO updating) {
        var room = chatRoomRepository.findById(updating.getId())
                .orElseThrow();

        room.setName(updating.getName());

        if (room.getType() == Type.GROUP) {
            var users = updating.getNewMembers()
                    .stream().map(dto -> userRepository.findByEmail(dto.getEmail()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();

            room.getMembers().clear();
            room.getMembers().addAll(users);
        }

        return chatRoomRepository.save(room);
    }

    public ChatRoom addMembers(AddChatRoomMembersDTO adding) {
        var room = chatRoomRepository.findById(adding.getId())
                .orElseThrow();

        if (room.getType() == Type.GROUP) {
            var members = room.getMembers();

            var addingUsers = adding.getAddingMembers()
                    .stream().map(dto -> userRepository.findByEmail(dto.getEmail()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .filter(u -> !members.contains(u))
                    .toList();

            room.getMembers().addAll(addingUsers);
        }

        return chatRoomRepository.save(room);
    }

    public ChatRoom removeMembers(RemoveChatRoomMembersDTO removing) {
        var room = chatRoomRepository.findById(removing.getId())
                .orElseThrow();

        if (room.getType() == Type.GROUP) {
            var members = room.getMembers();

            var removingUsers = removing.getRemovingMembers()
                    .stream().map(dto -> userRepository.findByEmail(dto.getEmail()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .filter(u -> members.stream().anyMatch(m -> m.getId() == u.getId()))
                    .toList();

            room.setMembers(room.getMembers()
                    .stream()
                    .filter(
                            m -> removingUsers
                                    .stream()
                                    .noneMatch(
                                            u -> u.getId() == m.getId()))
                    .toList());
        }

        return chatRoomRepository.save(room);
    }

    public void removeChat(Long id) {
        chatRoomRepository.deleteById(id);
    }
}
