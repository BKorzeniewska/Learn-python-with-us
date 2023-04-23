package com.example.learnpython.challenge;

import com.example.learnpython.challenge.exception.ChallengeNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.List;
import java.util.Objects;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final ChallengeMapper challengeMapper;
    private final JsonConverter jsonConverter;


    //TODO
    @Transactional
    public void executeChallenge(ExecuteChallengeRequest request) {
        Challenge challenge = challengeRepository
                .findById(request.challengeId())
                .orElseThrow(
                () -> new ChallengeNotFoundException("Challenge not found", "CHALLENGE_NOT_FOUND"));

        log.info("Executing challenge: {}", challenge);


        //TODO Execute python code code
        /*try (PythonInterpreter interpreter = new PythonInterpreter()) {
            StringWriter output = new StringWriter();
            interpreter.setOut(output);

            log.info("Executing user answer: {}", request.answer());
            interpreter.exec(request.answer());

            String userResult = output.toString();

            interpreter.exec(challenge.getContent().getCorrectAnswer());

            if (Objects.equals(output.toString().trim(), userResult.trim())) {
                log.info("Result: TAK");
                //test.setResult(TestResult.PASSED);
            } else {
                log.info("Result: WRONG");
                //test.setResult(TestResult.FAILED);
            }
        } catch(Exception e) {
            log.error("Error executing Python script: {}", e.getMessage());
        }*/

    }


    @Override
    public ChallengeResponse createChallenge(CreateChallengeRequest request) {
        //var challenge = challengeMapper.toChallenge(request);
        Challenge challenge = Challenge.builder()
                .name(request.getName())
                .content(jsonConverter.convertToEntityAttribute(request.getContent()))
                .question(request.getQuestion())
                .build();

        log.info("challenge: {}", challenge);
        challengeRepository.save(challenge);
        //return challengeMapper.toCreateChallengeResponse(challenge);

        return ChallengeResponse.builder()
                .question(challenge.getQuestion())
                .question(challenge.getQuestion())
                .content(jsonConverter.convertToDatabaseColumn(challenge.getContent()))
                .build();
    }

    @Override
    public List<ChallengeResponse> getChallengeByName(String name) {
        var challenges = challengeRepository
                .findByNameContaining(name)
                .orElseThrow(() -> new ChallengeNotFoundException(
                        "Challenges with provided name not found", "CHALLENGES_NOT_FOUND"));

        return challenges.stream()
                .map(challenge -> ChallengeResponse.builder()
                        .question(challenge.getQuestion())
                        .question(challenge.getQuestion())
                        .content(jsonConverter.convertToDatabaseColumn(challenge.getContent())).build())
                .toList();
    }

    @Override
    public ChallengeResponse getChallengeById(Long challengeId) {
        var challenge = challengeRepository
                .findById(challengeId)
                .orElseThrow(() -> new ChallengeNotFoundException(
                        "Challenge with provided ID not found", "CHALLENGE_NOT_FOUND"));
        //return challengeMapper.toCreateChallengeResponse(challenge);
        return ChallengeResponse.builder()
                .question(challenge.getQuestion())
                .question(challenge.getQuestion())
                .content(jsonConverter.convertToDatabaseColumn(challenge.getContent()))
                .build();
    }

    @Override
    public List<ChallengeResponse> getChallenges() {
        var challenges = challengeRepository.findAll();
        return challenges.stream()
                .map(challenge -> ChallengeResponse.builder()
                        .question(challenge.getQuestion())
                        .question(challenge.getQuestion())
                        .content(jsonConverter.convertToDatabaseColumn(challenge.getContent())).build())
                .toList();
    }

    @Override
    public List<ChallengeResponse> getChallengesByArticleId(Long articleId) {
        var challenges = challengeRepository
                .findByArticleId(articleId)
                .orElseThrow(() -> new ChallengeNotFoundException(
                        "Challenges with provided article id not found", "CHALLENGES_NOT_FOUND"));

        return  challenges.stream()
                .map(challenge -> ChallengeResponse.builder()
                        .question(challenge.getQuestion())
                        .question(challenge.getQuestion())
                        .content(jsonConverter.convertToDatabaseColumn(challenge.getContent())).build())
                .toList();
    }
}
