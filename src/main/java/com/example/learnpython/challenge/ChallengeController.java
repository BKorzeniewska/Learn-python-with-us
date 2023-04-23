package com.example.learnpython.challenge;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/challenge")
@RequiredArgsConstructor
public class ChallengeController {
    private final ChallengeService challengeService;
    @GetMapping
    public ResponseEntity<List<ChallengeResponse>> getChallenges() {
        var challenges = challengeService.getChallenges();
        return new ResponseEntity<>(challenges, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ChallengeResponse> getChallengeById(@PathVariable("id") Long challengeId) {
        var challenge = challengeService.getChallengeById(challengeId);
        return new ResponseEntity<>(challenge, HttpStatus.OK);
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<ChallengeResponse>> getChallengeByArticleId(@PathVariable("articleId") Long articleId) {
        var challenges = challengeService.getChallengesByArticleId(articleId);
        return new ResponseEntity<>(challenges, HttpStatus.OK);
    }

    @GetMapping("/name/{nameFragment}")
    public ResponseEntity<List<ChallengeResponse>> getChallengeByNameContaining(@PathVariable("nameFragment") String nameFragment) {
        var challenges = challengeService.getChallengeByName(nameFragment);
        return new ResponseEntity<>(challenges, HttpStatus.OK);
    }
}
