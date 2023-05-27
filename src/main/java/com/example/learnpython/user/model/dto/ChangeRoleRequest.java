package com.example.learnpython.user.model.dto;

import com.example.learnpython.user.model.entity.Role;

public record ChangeRoleRequest(
        Long userId,
        Role role
) {
}
