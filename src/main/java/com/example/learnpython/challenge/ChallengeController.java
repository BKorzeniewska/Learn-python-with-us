package com.example.learnpython.challenge;

import com.example.learnpython.article.ArticleResponse;
import com.example.learnpython.chapter.ChapterResponse;
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
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ChallengeResponse> getChallengeById(@PathVariable("id") Long challengeId) {
        var challenge = challengeService.getChallengeById(challengeId);
        return new ResponseEntity<>(challenge, HttpStatus.OK);
    }
    @GetMapping("/name/{nameFragment}")
    public ResponseEntity<List<ChallengeResponse>> getChallengeByNameContaining(@PathVariable("nameFragment") String nameFragment) {
        var challenge = challengeService.getChallengeByName(nameFragment);
        return new ResponseEntity<>(challenge, HttpStatus.OK);
    }

}
