package com.example.learnpython.challenge.model;

public record ExecuteChallengeRequest(
        long challengeId,
        Type type,
        String answer
) {
}
