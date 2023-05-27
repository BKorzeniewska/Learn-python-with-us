package com.example.learnpython.user.model.dto;

import lombok.Builder;

@Builder
public record UserInfoResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String nickname,
        Integer level,
        Long exp,
        Integer challengesSolvedCount
) {
}
