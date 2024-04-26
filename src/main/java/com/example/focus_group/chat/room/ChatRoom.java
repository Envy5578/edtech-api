package com.example.focus_group.chat.room;

import com.example.focus_group.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat_rooms")
public class ChatRoom {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE
    )
    @JoinTable(
            name = "rooms_members",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private List<User> members;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Override
    public String toString() {
        return String.format("id - '%s', name - '%s', type - '%s', members_count - '%s'",
                id, name, type.name(), members.size());
    }
}
