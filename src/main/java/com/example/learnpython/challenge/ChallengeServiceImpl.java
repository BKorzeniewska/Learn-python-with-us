package com.example.learnpython.challenge;

import com.example.learnpython.article.Article;
import com.example.learnpython.article.ArticleRepository;
import com.example.learnpython.article.exception.ArticleNotFoundException;
import com.example.learnpython.challenge.exception.ChallengeNotFoundException;
import com.example.learnpython.challenge.model.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
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
    private final ArticleRepository articleRepository;
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
            Map<String,List<String>> answers= challenge.getContent().getCode();
            List<String> results=answers.get("results");
            List<String>inputs=answers.get("inputs");
            log.info("Executing user answer: {}", request.answer());
            for( int i=0;i<results.size();i++)
            {
                interpreter.setOut(outputUser);
                // Validate input
//                if (!isValidInput(request.answer())) {
//                    throw new IllegalArgumentException("Invalid input");
//                }
                String[] input=inputs.get(i).split(" " );
                for(int j=0;j<input.length;j++)
                {
                    interpreter.exec(input[j]);
                    System.out.println(input[i]);
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


    // TODO
    //  to adopt
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

    @Transactional
    @Override
    public ChallengeResponse createChallenge(CreateChallengeRequest request) {
        //var challenge = challengeMapper.toChallenge(request);
        Challenge challenge = Challenge.builder()
                .name(request.getName())
                .content(jsonConverter.convertToEntityAttribute(request.getContent()))
                .question(request.getQuestion())
                .type(request.getType())
                .build();
        List<Article> articles = new ArrayList<>();
        for (Long id : request.getArticlesID()) {
            final Article article = articleRepository
                    .findById(id)
                    .orElseThrow(() -> new ArticleNotFoundException(
                            "Article with provided ID not found", "ARTICLE_NOT_FOUND"));
            articles.add(article);

        }
        challenge.setArticles(articles);
        challengeRepository.save(challenge);
        for (Article article : articles) {
            Hibernate.initialize(article);
            Hibernate.initialize(article.getChallenges());
            article.getChallenges().add(challenge);
            articleRepository.save(article);
        }

        log.info("challenge: {}", challenge);

        return ChallengeResponse.builder()
                .id(challenge.getId())
                .name(challenge.getName())
                .question(challenge.getQuestion())
                .type(challenge.getType())
                .content(jsonConverter.convertToDatabaseColumn(challenge.getContent()))
                .articlesID((List<Long>) request.getArticlesID())
                .build();
    }

    @Override
    public List<ChallengeResponse> getChallengeByName(String name) {
        var challenges = challengeRepository
                .findByNameContaining(name)
                .orElseThrow(() -> new ChallengeNotFoundException(
                        "Challenges with provided name not found", "CHALLENGES_NOT_FOUND"));

        return challenges.stream()
                .map(challenge -> challengeMapper.toCreateChallengeResponse(challenge,jsonConverter))
                .toList();
    }

    @Override
    public ChallengeResponse getChallengeById(Long challengeId) {
        var challenge = challengeRepository
                .findById(challengeId)
                .orElseThrow(() -> new ChallengeNotFoundException(
                        "Challenge with provided ID not found", "CHALLENGE_NOT_FOUND"));

        return challengeMapper.toCreateChallengeResponse(challenge,jsonConverter);
    }

    @Override
    public List<ChallengeResponse> getChallenges() {
        var challenges = challengeRepository.findAll();
        return challenges.stream()
                .map(challenge -> challengeMapper.toCreateChallengeResponse(challenge,jsonConverter))
                .toList();
    }
    @Transactional
    @Override
    public List<ChallengeResponse> getChallengesByArticleId(Long articleId) {
        var challenges = challengeRepository
                .findByArticleId(articleId)
                .orElseThrow(() -> new ChallengeNotFoundException(
                        "Challenges with provided article id not found", "CHALLENGES_NOT_FOUND"));

        return challenges.stream()
                .map(challenge -> challengeMapper.toCreateChallengeResponse(challenge,jsonConverter))
                .toList();
    }

    @Transactional
    @Override
    public ExecutedChallengeResponse execute(ExecuteChallengeRequest request) {
        Challenge challenge = challengeRepository
                .findById(request.challengeId())
                .orElseThrow(
                        () -> new ChallengeNotFoundException("Challenge not found", "CHALLENGE_NOT_FOUND"));
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
}
