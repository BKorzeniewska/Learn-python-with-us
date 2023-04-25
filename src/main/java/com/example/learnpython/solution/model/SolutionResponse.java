package com.example.learnpython.solution.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SolutionResponse {
    private long id;
    private LocalDateTime attemptedAt;
    private String answer;
    private Long userId;
    private Long challengeId;
}
