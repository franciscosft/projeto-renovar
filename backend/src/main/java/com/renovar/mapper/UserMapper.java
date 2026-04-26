package com.renovar.mapper;

import org.springframework.stereotype.Component;

import com.renovar.domain.User;
import com.renovar.dto.UserDTO;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getEmail());
    }

}