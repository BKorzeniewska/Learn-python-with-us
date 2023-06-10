package com.example.learnpython.challenge.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserChallengeResponse(
        long challengeId,
        long solutionId,
        String challengeName,
        long userId,
        String answer,
        LocalDateTime attemptedAt
) {
}
