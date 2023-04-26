package com.example.learnpython.solution;

import com.example.learnpython.solution.exception.AddSolutionException;
import com.example.learnpython.solution.exception.SolutionNotFoundException;
import com.example.learnpython.solution.model.SolutionRequest;
import com.example.learnpython.solution.model.SolutionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class SolutionServiceImpl implements SolutionService {
    private final SolutionRepository solutionRepository;
    private final SolutionMapper solutionMapper;

    @Override
    public long addUserSolution(SolutionRequest solutionRequest) {

        if (solutionRequest.getUserId() == null || solutionRequest.getChallengeId() == null) {

            log.error("User id or challenge id is null");
            throw new AddSolutionException("UserId or challengeId cannot be null",
                    "USER_ID_OR_CHALLENGE_ID_IS_NULL");
        }

        final Solution solution = solutionMapper.toSolution(solutionRequest);
        solutionRepository.save(solution);
        return solution.getId();
    }

    @Override
    public SolutionResponse getUserSolution(Long userId, Long challengeId) {

        if (userId == null || challengeId == null) {

            log.error("User id or challenge id is null");
            throw new AddSolutionException("UserId or challengeId cannot be null",
                    "USER_ID_OR_CHALLENGE_ID_IS_NULL");
        }

        final Solution solution = solutionRepository.findByUserIdAndChallengeId(userId, challengeId);
        if (solution == null) {

            log.error("Solution not found with userId: {} and challengeId: {}", userId, challengeId);
            throw new SolutionNotFoundException("Solution not found",
                    "SOLUTION_NOT_FOUND");
        }
        return solutionMapper.toSolutionResponse(solution);
    }
}
