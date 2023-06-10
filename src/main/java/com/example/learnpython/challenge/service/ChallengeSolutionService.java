package com.example.learnpython.challenge.service;

import com.example.learnpython.challenge.model.UserChallengeResponse;

import java.util.List;

public interface ChallengeSolutionService {
    List<UserChallengeResponse> findSolvedChallengesByUserId(final long userId);
}
