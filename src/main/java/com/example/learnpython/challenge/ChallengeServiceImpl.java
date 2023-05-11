package com.example.learnpython.challenge;

import com.example.learnpython.challenge.exception.ChallengeNotFoundException;
import com.example.learnpython.challenge.model.ChallengeResponse;
import com.example.learnpython.challenge.model.CreateChallengeRequest;
import com.example.learnpython.challenge.model.ExecuteChallengeRequest;
import com.example.learnpython.challenge.model.Type;
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

    @Transactional
    public void executeChallenge(ExecuteChallengeRequest request) {
        Challenge challenge = challengeRepository
                .findById(request.challengeId())
                .orElseThrow(
                        () -> new ChallengeNotFoundException("Challenge not found", "CHALLENGE_NOT_FOUND"));
        log.info("Executing challenge: {}", challenge);

        // Validate input
        if (!isValidInput(request.answer())) {
            throw new IllegalArgumentException("Invalid input");
        }

        // Execute python code code
        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            StringWriter output = new StringWriter();
            interpreter.setOut(output);

            log.info("Executing user answer: {}", request.answer());

            // Set resource limits
            interpreter.exec("import resource\nresource.setrlimit(resource.RLIMIT_CPU, (1,1))");
            interpreter.exec("resource.setrlimit(resource.RLIMIT_DATA, (1024,1024))");

            // Execute code in an isolated environment
            interpreter.exec("import sys");
            interpreter.exec("sys.path = []");
            interpreter.exec("del sys");

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
        } catch (Exception e) {
            log.error("Error executing Python script: {}", e.getMessage());
        }
    }

    // TODO
    //  to adopt
    // This function checks whether each command in user code starts with one of the allowed expressions (e.g. print, input, int, float, str, bool).
    // If the command does not start with a valid expression, the method returns false, which means the input is invalid.
    // Otherwise, it returns true, which means the input is safe to execute.

    private boolean isValidInput(String input) {
        String[] allowedExpressions = {"print", "input", "int", "float", "str", "bool"};
        String[] lines = input.split("\n");
        for (String line : lines) {
            String[] tokens = line.split("\\s");
            if (tokens.length > 0) {
                boolean isValid = false;
                for (String expression : allowedExpressions) {
                    if (tokens[0].startsWith(expression)) {
                        isValid = true;
                        break;
                    }
                }
                if (!isValid) {
                    return false;
                }
            }
        }
        return true;
    }

    @Transactional
    public void executeClosedChallenge(ExecuteChallengeRequest request) {
        Challenge challenge = challengeRepository
                .findById(request.challengeId())
                .orElseThrow(
                        () -> new ChallengeNotFoundException("Challenge not found", "CHALLENGE_NOT_FOUND"));

        log.info("Executing challenge: {}", challenge);

        if(request.answer().equals(challenge.getContent().getCorrectAnswer()))
        {
            log.info("Result: GOOD SOUP");
        }
        else {
            log.info("Result: WRONG");
        }
    }


    @Override
    public ChallengeResponse createChallenge(CreateChallengeRequest request) {
        //var challenge = challengeMapper.toChallenge(request);
        Challenge challenge = Challenge.builder()
                .name(request.getName())
                .content(jsonConverter.convertToEntityAttribute(request.getContent()))
                .question(request.getQuestion())
                .type(request.getType())
                .build();
        log.info("challenge: {}", challenge);
        challengeRepository.save(challenge);
        return ChallengeResponse.builder()
                .name(challenge.getName())
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

    @Override
    public void execute(ExecuteChallengeRequest request) {
        if(request.type().equals(Type.CODE))
        {
            executeChallenge(request);
        } else if (request.type().equals(Type.CLOSED)||request.type().equals(Type.OPEN)) {
            executeClosedChallenge(request);

        }
    }
}
