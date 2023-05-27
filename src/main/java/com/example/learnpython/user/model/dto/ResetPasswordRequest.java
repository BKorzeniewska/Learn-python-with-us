package com.example.learnpython.user.model.dto;

import lombok.Builder;

@Builder
public record ResetPasswordRequest(
        String email,
        String token,
        String newPassword
) {
}
