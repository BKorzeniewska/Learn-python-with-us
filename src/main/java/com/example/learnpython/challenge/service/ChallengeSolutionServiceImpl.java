package com.example.learnpython.challenge.service;


import com.example.learnpython.challenge.model.UserChallengeResponse;
import com.example.learnpython.solution.Solution;
import com.example.learnpython.solution.SolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ChallengeSolutionServiceImpl implements ChallengeSolutionService {
    private final SolutionRepository solutionRepository;

    @Override
    public List<UserChallengeResponse> findSolvedChallengesByUserId(final long userId) {

        final List<Solution> solutions = solutionRepository.findByUserId(userId);

        return solutions.stream().map(solution ->
                UserChallengeResponse.builder()
                        .challengeId(solution.getChallenge().getId())
                        .challengeName(solution.getChallenge().getName())
                        .solutionId(solution.getId())
                        .attemptedAt(solution.getAttemptedAt())
                        .answer(solution.getAnswer())
                        .userId(solution.getUser().getId())
                    .build())
            .toList();
    }
}
