package com.example.focus_group.chat.room;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.focus_group.user.User;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findByMembersContains(User member);
}