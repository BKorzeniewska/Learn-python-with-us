package com.example.learnpython.solution.service;

import com.example.learnpython.article.exception.ArticleNotFoundException;
import com.example.learnpython.challenge.Challenge;
import com.example.learnpython.challenge.exception.ChallengeNotFoundException;
import com.example.learnpython.challenge.repository.ChallengeRepository;
import com.example.learnpython.solution.model.Solution;
import com.example.learnpython.solution.SolutionMapper;
import com.example.learnpython.solution.exception.AddSolutionException;
import com.example.learnpython.solution.exception.SolutionNotFoundException;
import com.example.learnpython.solution.model.SolutionRequest;
import com.example.learnpython.solution.model.SolutionResponse;
import com.example.learnpython.solution.repository.SolutionRepository;
import com.example.learnpython.user.model.entity.User;
import com.example.learnpython.user.repository.UserRepository;
import com.example.learnpython.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class SolutionServiceImpl implements SolutionService {
    private final SolutionRepository solutionRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;

    private final SolutionMapper solutionMapper;

    @Override
    public long addUserSolution(SolutionRequest solutionRequest, final String bearerToken) {
        final String token = bearerToken.substring(7);
        final User user = userRepository.findByToken(token)
                .orElseThrow(() -> new ArticleNotFoundException("User with provided token not found", "USER_NOT_FOUND"));

        if (solutionRequest.getChallengeId() == null) {
            log.error(" challenge id is null");
            throw new AddSolutionException("ChallengeId cannot be null", "CHALLENGE_ID_IS_NULL");
        }

        final Challenge challenge = challengeRepository.findById(solutionRequest.getChallengeId())
                .orElseThrow(() -> new ChallengeNotFoundException("Challenge not found", "CHALLENGE_NOT_FOUND"));

        final Optional<Solution> existingSolution = solutionRepository.findByUserIdAndChallengeId(user.getId(), solutionRequest.getChallengeId());

        if (!existingSolution.isPresent() && solutionRequest.isCorrect()) {
            userService.updateUserLevelAndExpById(user.getId(), challenge.getExp().intValue());
        }

        final Solution solution = Solution.builder()
                .answer(solutionRequest.getAnswer())
                .user(user)
                .attemptedAt(LocalDateTime.now())
                .challenge(challenge)
                .build();
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

        final Solution solution = solutionRepository.findByUserIdAndChallengeId(userId, challengeId)
                .orElseThrow(
                        () -> new SolutionNotFoundException("Solution not found", "CHALLENGE_NOT_FOUND"));
        ;
        if (solution == null) {

            log.error("Solution not found with userId: {} and challengeId: {}", userId, challengeId);
            throw new SolutionNotFoundException("Solution not found",
                    "SOLUTION_NOT_FOUND");
        }
        return solutionMapper.toSolutionResponse(solution);
    }
}
