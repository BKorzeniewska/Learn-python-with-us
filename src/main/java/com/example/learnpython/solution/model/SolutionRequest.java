package com.example.learnpython.solution.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SolutionRequest {
    private String answer;
    private Long userId;
    private Long challengeId;
}
