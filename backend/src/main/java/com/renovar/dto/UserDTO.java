package com.renovar.dto;

import com.renovar.domain.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserDTO(
        @NotEmpty(message = "Name is required") String name,
        String lastName,
        @Email(message = "Invalid email") String email
) {
    public static UserDTO from(User user) {
        return new UserDTO(user.getName(), user.getLastName(), user.getEmail());
    }
}