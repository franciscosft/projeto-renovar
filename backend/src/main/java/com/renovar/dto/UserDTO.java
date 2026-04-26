package com.renovar.dto;

import com.renovar.domain.User;

import jakarta.validation.constraints.Email;

public record UserDTO(
        @Email(message = "Invalid email") String email
) {
    public static UserDTO from(User user) {
        return new UserDTO(user.getEmail());
    }
}