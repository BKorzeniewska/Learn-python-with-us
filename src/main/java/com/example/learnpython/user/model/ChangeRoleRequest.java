package com.example.learnpython.user.model;

import com.example.learnpython.user.Role;

public record ChangeRoleRequest(
        Long userId,
        Role role
) {
}
