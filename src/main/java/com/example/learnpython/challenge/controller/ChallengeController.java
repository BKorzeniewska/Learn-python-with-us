package com.example.learnpython.challenge.controller;

import com.example.learnpython.challenge.model.ChallengeResponse;
import com.example.learnpython.challenge.model.ExecuteChallengeRequest;
import com.example.learnpython.challenge.model.ExecutedChallengeResponse;
import com.example.learnpython.challenge.service.ChallengeService;
import com.example.learnpython.solution.service.SolutionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/challenge")
@RequiredArgsConstructor
public class ChallengeController {
    private final ChallengeService challengeService;
    private final SolutionService solutionService;

    @GetMapping
    public ResponseEntity<List<ChallengeResponse>> getChallenges( @RequestHeader(value = "Authorization", required = false)  String bearerToken) {
        var challenges = challengeService.getChallenges(bearerToken);
        return new ResponseEntity<>(challenges, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeResponse> getChallengeById(@PathVariable("id") Long challengeId, @RequestHeader(value = "Authorization", required = false)  String bearerToken) {
        var challenge = challengeService.getChallengeById(challengeId, bearerToken);
        return new ResponseEntity<>(challenge, HttpStatus.OK);
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<ChallengeResponse>> getChallengeByArticleId(@PathVariable("articleId") Long articleId, @RequestHeader(value = "Authorization", required = false)  String bearerTokend) {
        var challenges = challengeService.getChallengesByArticleId(articleId, bearerTokend);
        return new ResponseEntity<>(challenges, HttpStatus.OK);
    }

    @GetMapping("/name/{nameFragment}")
    public ResponseEntity<List<ChallengeResponse>> getChallengeByNameContaining(@PathVariable("nameFragment") String nameFragment, @RequestHeader(value = "Authorization", required = false)  String bearerToken) {
        var challenges = challengeService.getChallengeByName(nameFragment, bearerToken);
        return new ResponseEntity<>(challenges, HttpStatus.OK);
    }
    // TODO
    // Agree with place where we add exp point to user's point for good answer
    @PostMapping("/execute")
    @Operation(summary = "Execute a challenge")
    public ResponseEntity<ExecutedChallengeResponse> executeChallenge(@RequestBody ExecuteChallengeRequest executeChallengeRequest) {
        return ResponseEntity.ok(challengeService.execute(executeChallengeRequest));
    }
}
