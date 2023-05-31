package com.example.learnpython.user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ResetPasswordRequest(
        @NotNull @Email String email,
        @NotNull String token,
        @NotNull String newPassword
) {
}
