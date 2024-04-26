package com.example.focus_group.auth.mapper;

import com.example.focus_group.auth.dto.UserDTO;
import com.example.focus_group.auth.entities.UserEntity;

public class UserMapper {
    public static UserDTO toDTO(UserEntity user) {
        return new UserDTO(user.getFirstName(), user.getLastName(), user.getEmail());
    }
}
