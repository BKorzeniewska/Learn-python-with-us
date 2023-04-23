package com.example.learnpython.challenge;

public record ExecuteChallengeRequest(
        long challengeId,
        String answer
) {
}
