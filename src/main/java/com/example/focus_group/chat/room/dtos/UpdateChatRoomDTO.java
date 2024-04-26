package com.example.focus_group.chat.room.dtos;

import com.example.focus_group.user.dtos.UserDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateChatRoomDTO {
    private Long id;
    private String name;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<UserDTO> newMembers;
}
