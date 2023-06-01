package com.example.learnpython.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Name is mandatory")
    private String firstname;

    @NotBlank(message = "Nickname is mandatory")
    private String nickname;

    @NotBlank(message = "Lastname is mandatory")
    private String lastname;

    @Email
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Min(8)
    @NotBlank(message = "Password is mandatory")
    private String password;
}