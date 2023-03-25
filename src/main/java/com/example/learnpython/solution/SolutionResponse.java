package com.example.learnpython.solution;

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
