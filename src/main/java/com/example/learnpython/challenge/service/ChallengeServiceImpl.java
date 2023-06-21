package com.example.learnpython.challenge.service;

import com.example.learnpython.article.exception.ArticleNotFoundException;
import com.example.learnpython.article.repository.ArticleRepository;
import com.example.learnpython.challenge.Challenge;
import com.example.learnpython.challenge.ChallengeMapper;
import com.example.learnpython.challenge.repository.ChallengeRepository;
import com.example.learnpython.challenge.JsonConverter;
import com.example.learnpython.challenge.exception.ChallengeNotFoundException;
import com.example.learnpython.challenge.model.*;
import com.example.learnpython.solution.model.Solution;
import com.example.learnpython.solution.repository.SolutionRepository;
import com.example.learnpython.user.exception.UserNotFoundException;
import com.example.learnpython.user.exception.UserRequestException;
import com.example.learnpython.user.model.entity.User;
import com.example.learnpython.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.*;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;

    private final UserRepository userRepository;

    private final SolutionRepository solutionRepository;

    private final ChallengeMapper challengeMapper;

    private final JsonConverter jsonConverter;

    @Transactional
    public ExecutedChallengeResponse executeChallenge(ExecuteChallengeRequest request) {
        Challenge challenge = challengeRepository
                .findById(request.challengeId())
                .orElseThrow(
                        () -> new ChallengeNotFoundException("Challenge not found", "CHALLENGE_NOT_FOUND"));

        log.info("Executing challenge: {}", challenge);

        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            StringWriter outputUser = new StringWriter();
            StringWriter outputServer = new StringWriter();

            Map<String, List<String>> answers = challenge.getContent().getCode();
            List<String> results = answers.get("results");
            List<String> inputs = answers.get("inputs");
            log.info("Executing user answer: {}", request.answer());

            for (int i = 0; i < results.size(); i++) {
                interpreter.setOut(outputUser);
                // Validate input
                if (!isValidInput(request.answer())) {
                    throw new IllegalArgumentException("Invalid input");
                }
                String[] input = inputs.get(i).split(" ");
                for (int j = 0; j < input.length; j++) {
                    interpreter.exec(input[j]);
                    System.out.println(input[j]);
                }

                interpreter.exec(request.answer());
                interpreter.exec(answers.get("toPrint").get(0));
                interpreter.setOut(outputServer);
                interpreter.exec(results.get(i));
                interpreter.exec(answers.get("toPrint").get(0));
            }

            if (Objects.equals(outputServer.toString().trim(), outputUser.toString().trim())) {
                log.info("Result: TAK");
                return ExecutedChallengeResponse.builder()
                        .challengeId(challenge.getId())
                        .result(Result.SUCCESS)
                        .output(outputServer.toString().trim())
                        .build();
            } else {
                log.info("Result: WRONG");
                return ExecutedChallengeResponse.builder()
                        .challengeId(challenge.getId())
                        .result(Result.FAIL)
                        .output(outputServer.toString().trim())
                        .build();
            }
        } catch (Exception e) {
            log.error("Error executing Python script: {}", e.getMessage());
            return ExecutedChallengeResponse.builder()
                    .challengeId(challenge.getId())
                    .result(Result.FAIL)
                    .output(e.getMessage())
                    .build();
        }
    }


    // This function checks whether each command in user code starts with one of the allowed expressions (e.g. print, input, int, float, str, bool).
    // If the command does not start with a valid expression, the method returns false, which means the input is invalid.
    // Otherwise, it returns true, which means the input is safe to execute.

    private boolean isValidInput(String input) {
        String[] allowedExpressions = {"print", "input", "int", "float", "str", "bool", "def"};
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
                    if (tokens[0].matches("^\\d+(\\.\\d+)?([+\\-*/]\\d+(\\.\\d+)?)*$") ||
                            tokens[0].matches("^\\d+(\\.\\d+)?(e[+-]?\\d+)?$")) {
                        isValid = true;
                    } else {
                        if (tokens[0].startsWith("def") && tokens[tokens.length - 1].endsWith(":")) {
                            isValid = true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    @Transactional
    public ExecutedChallengeResponse executeClosedChallenge(ExecuteChallengeRequest request) {
        Challenge challenge = challengeRepository
                .findById(request.challengeId())
                .orElseThrow(
                        () -> new ChallengeNotFoundException("Challenge not found", "CHALLENGE_NOT_FOUND"));

        log.info("Executing challenge: {}", challenge);

        if (request.answer().equals(challenge.getContent().getCorrectAnswer())) {
            log.info("Result: GOOD SOUP");
            return ExecutedChallengeResponse.builder()
                    .challengeId(challenge.getId())
                    .result(Result.SUCCESS)
                    .build();
        } else {
            log.info("Result: WRONG");
            return ExecutedChallengeResponse.builder()
                    .challengeId(challenge.getId())
                    .result(Result.FAIL)
                    .build();
        }
    }


    @Override
    public List<ChallengeResponse> getChallengeByName(final String name, final String... bearerToken) {
        log.info("getChallengeByName() - start - name: {}", name);
        final List<Challenge> challenges = getChallengeListContaining(name, bearerToken);
        final List<ChallengeResponse> challengeResponses = challenges.stream()
                .map(challenge -> challengeMapper.toCreateChallengeResponse(challenge, jsonConverter, this, bearerToken[0]))
                .toList();
        log.info("getChallengeByName() - end - name: {}", name);
        return challengeResponses;
    }

    @Override
    public ChallengeResponse getChallengeById(Long challengeId, String... bearerToken) {
        var challenge = challengeRepository
                .findById(challengeId)
                .orElseThrow(() -> new ChallengeNotFoundException("Challenge with provided ID not found", "CHALLENGE_NOT_FOUND"));
        ChallengeResponse response = challengeMapper.toCreateChallengeResponse(challenge, jsonConverter, this, bearerToken[0]);
        response.setDone(itsDone(challenge, bearerToken[0]));
        return response;
    }

    @Override
    public List<ChallengeResponse> getChallenges(String... bearerToken) {
        log.info("getChallenges() - start");
        final List<Challenge> challenges = getChallengeList(bearerToken);
        final List<ChallengeResponse> challengeResponses = challenges.stream()
                .map(challenge -> challengeMapper.toCreateChallengeResponse(challenge, jsonConverter, this, bearerToken[0]))
                .toList();
        log.info("getChallenges() - end - challengeResponses={}", challengeResponses);
        return challengeResponses;
    }

    @Transactional
    @Override
    public List<ChallengeResponse> getChallengesByArticleId(final Long articleId, String... bearerToken) {
        log.info("getChallengesByArticleId() - start - articleId={}", articleId);
        final List<Challenge> challenges = getChallengeListById(articleId, bearerToken);
        log.info("getChallengesByArticleId() - end - challenges={}", challenges);
        return challenges.stream()
                .map(challenge -> challengeMapper.toCreateChallengeResponse(challenge, jsonConverter, this, bearerToken[0]))
                .toList();
    }

    @Transactional
    @Override
    public ExecutedChallengeResponse execute(ExecuteChallengeRequest request) {
        Challenge challenge = challengeRepository
                .findById(request.challengeId())
                .orElseThrow(() -> new ChallengeNotFoundException("Challenge not found", "CHALLENGE_NOT_FOUND"));
        if (request.type().equals(Type.CODE) && request.type().equals(challenge.getType())) {
            return executeChallenge(request);
        } else if ((request.type().equals(Type.OPEN) || request.type().equals(Type.CLOSED) && request.type().equals(challenge.getType()))
        ) {
            return executeClosedChallenge(request);
        } else {
            return ExecutedChallengeResponse.builder()
                    .challengeId(challenge.getId())
                    .result(Result.FAIL)
                    .build();
        }
    }

    public boolean itsDone(Challenge challenge, String bearerToken) {
        if (bearerToken == null || bearerToken.isEmpty()) {
            return false;
        } else {
            final String token = bearerToken.substring(7);
            final User user = userRepository.findByToken(token)
                    .orElseThrow(() -> new ArticleNotFoundException("User with provided token not found", "USER_NOT_FOUND"));

            final Optional<Solution> existingSolution = solutionRepository.findByUserIdAndChallengeId(user.getId(), challenge.getId());
            return existingSolution.isPresent();
        }
    }

    private List<Challenge> getChallengeListContaining(final String name, final String[] bearerToken) {
        log.info("getChallengeListContaining() - start - getting challenges by name - {}", name);
        final User user = checkTokenGetUserOrNull(bearerToken);
        List<Challenge> challenges;

        if (user == null) {
            challenges = challengeRepository.findByNameContaining(name, null);
        } else if (user.getRole().getValue() < 3) {
            challenges = challengeRepository.findByNameContaining(name, user.getId());
        } else {
            challenges = challengeRepository.findByNameContaining(name);
        }
        log.info("getChallengeListContaining() - end - found challenges - {}", challenges);
        return challenges;
    }

    private List<Challenge> getChallengeListById(final Long id, final String[] bearerToken) {
        log.info("getChallengeListById() - start - getting challenges by id - {}", id);
        final User user = checkTokenGetUserOrNull(bearerToken);
        List<Challenge> challenges;

        if (user == null) {
            challenges = challengeRepository.findByArticleId(id, null);
        } else if (user.getRole().getValue() < 3) {
            challenges = challengeRepository.findByArticleId(id, user.getId());
        } else {
            challenges = challengeRepository.findByArticleId(id);
        }
        log.info("getChallengeListById() - end - found challenges - {}", challenges);
        return challenges;
    }

    private List<Challenge> getChallengeList(final String[] bearerToken) {
        log.info("getChallengeList() - start - getting challenges");
        final User user = checkTokenGetUserOrNull(bearerToken);
        List<Challenge> challenges;

        if (user == null) {
            challenges = challengeRepository.findAllChallenges(null);
        } else if (user.getRole().getValue() < 3) {
            challenges = challengeRepository.findAllChallenges(user.getId());
        } else {
            challenges = challengeRepository.findAll();
        }
        log.info("getChallengeList() - end - found challenges - {}", challenges);
        return challenges;
    }

    private User checkTokenGetUserOrNull(final String[] bearerToken) {
        if (bearerToken.length == 0 || bearerToken[0] == null || !bearerToken[0].startsWith("Bearer ")) {
            log.info("checkTokenGetUserOrNull() - end - token is null");
            return null;
        }
        final String jwt = bearerToken[0].substring(7);
        return userRepository.findByToken(jwt).orElse(null);
    }

}
