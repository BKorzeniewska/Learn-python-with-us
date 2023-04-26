package com.example.learnpython.solution;

import com.example.learnpython.solution.model.SolutionRequest;
import com.example.learnpython.solution.model.SolutionResponse;

public interface SolutionService {
    long addUserSolution(SolutionRequest solutionRequest);
    SolutionResponse getUserSolution(Long userId, Long challengeId);
}
