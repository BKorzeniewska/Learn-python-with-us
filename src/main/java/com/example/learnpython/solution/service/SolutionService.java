package com.example.learnpython.solution.service;

import com.example.learnpython.solution.model.SolutionRequest;
import com.example.learnpython.solution.model.SolutionResponse;

public interface SolutionService {
    long addUserSolution(SolutionRequest solutionRequest,final String bearerToken);
    SolutionResponse getUserSolution(Long userId, Long challengeId);
}
