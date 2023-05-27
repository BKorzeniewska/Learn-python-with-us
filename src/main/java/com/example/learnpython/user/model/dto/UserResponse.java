package com.example.learnpython.user.model.dto;

import com.example.learnpython.user.model.entity.Role;

public record UserResponse(
        Long id,
        String firstname,
        String nickname,
        String lastname,
        String email,
        Role role
) {
}
