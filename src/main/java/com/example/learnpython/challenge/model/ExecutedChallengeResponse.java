package com.example.learnpython.challenge.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExecutedChallengeResponse {
    private long challengeId;
    private Result result;
}