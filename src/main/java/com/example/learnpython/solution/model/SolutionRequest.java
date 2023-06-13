package com.example.learnpython.solution.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SolutionRequest {
    private String answer;
    private boolean correct;
    private Long challengeId;
}
