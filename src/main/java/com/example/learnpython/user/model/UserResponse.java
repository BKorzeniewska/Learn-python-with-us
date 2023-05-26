package com.example.learnpython.user.model;

public record UserResponse(
        Long id,
        String firstname,
        String nickname,
        String lastname,
        String email
) {
}
