package com.example.learnpython.challenge;

import com.example.learnpython.challenge.exception.ChallengeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import lombok.extern.log4j.Log4j2;


@Log4j2
@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final ChallengeMapper challengeMapper;

    @Override
    public ChallengeResponse createChallenge(CreateChallengeRequest request) {
        var challenge = challengeMapper.toChallenge(request);
        log.info("challenge: {}", challenge);
        challengeRepository.save(challenge);
        return challengeMapper.toCreateChallengeResponse(challenge);
    }

    @Override
    public List<ChallengeResponse> getChallengeByName(String name) {
        var challenges = challengeRepository
                .findByNameContaining(name)
                .orElseThrow(() -> new ChallengeNotFoundException(
                        "Challenges with provided name not found", "CHALLENGES_NOT_FOUND"));

        return challenges.stream()
                .map(challengeMapper::toCreateChallengeResponse)
                .toList();
    }

    @Override
    public ChallengeResponse getChallengeById(Long challengeId) {
        var challenge = challengeRepository
                .findById(challengeId)
                .orElseThrow(() -> new ChallengeNotFoundException(
                        "Challenge with provided ID not found", "CHALLENGE_NOT_FOUND"));
        return challengeMapper.toCreateChallengeResponse(challenge);
    }

    @Override
    public List<ChallengeResponse> getChallenges() {
        var challenges = challengeRepository.findAll();
        return challenges.stream().map(challengeMapper::toChallengeResponse).toList();
    }

    @Override
    public List<ChallengeResponse> getChallengesByArticleId(Long articleId) {
        var challenges = challengeRepository
                .findByArticleId(articleId)
                .orElseThrow(() -> new ChallengeNotFoundException(
                        "Challenges with provided article id not found", "CHALLENGES_NOT_FOUND"));

        return challenges.stream()
                .map(challengeMapper::toCreateChallengeResponse)
                .toList();
    }
}
