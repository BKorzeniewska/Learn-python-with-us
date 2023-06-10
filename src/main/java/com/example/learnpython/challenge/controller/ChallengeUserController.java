package com.example.learnpython.challenge.controller;

import com.example.learnpython.challenge.service.ChallengeSolutionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/user/v1/challenge")
@RequiredArgsConstructor
@Log4j2
public class ChallengeUserController {

    private final ChallengeSolutionService challengeSolutionService;

    @GetMapping("/solved/{userId}")
    @Operation(summary = "Find solved challenges by user id")
    public ResponseEntity<?> findSolvedChallengesByUserId(@PathVariable("userId") final long userId) {
        log.info("findSolvedChallengesByUserId() - start: {}", userId);
        var challenges = challengeSolutionService.findSolvedChallengesByUserId(userId);
        log.info("findSolvedChallengesByUserId() - end");
        return new ResponseEntity<>(challenges, HttpStatus.OK);
    }

}
