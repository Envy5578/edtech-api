package com.example.focus_group.chat.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query(value = """
      select t from ChatMessage t inner join User u\s
      on t.sender.id = u.id\s
      where u.email = :email\s
      """)
    List<ChatMessage> findByUserEmail(String email);

    @Query(value = """
      select t from ChatMessage t inner join ChatRoom cr\s
      on t.room.id = cr.id\s
      where cr.id = :id\s
      """)
    List<ChatMessage> findByChatRoomId(Long id);
}
