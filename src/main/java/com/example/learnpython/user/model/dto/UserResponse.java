package com.example.learnpython.user.model.dto;

public record UserResponse(
        Long id,
        String firstname,
        String nickname,
        String lastname,
        String email
) {
}
