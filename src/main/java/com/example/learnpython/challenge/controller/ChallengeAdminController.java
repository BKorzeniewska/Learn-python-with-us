package com.example.learnpython.challenge.controller;

import com.example.learnpython.challenge.model.ChallengeResponse;
import com.example.learnpython.challenge.model.CreateChallengeRequest;
import com.example.learnpython.challenge.model.ModifyChallengeRequest;
import com.example.learnpython.challenge.model.VisibleChangeRequest;
import com.example.learnpython.challenge.service.ChallengeAdminService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/v1/challenge")
@RequiredArgsConstructor
@Log4j2
public class ChallengeAdminController {
    private final ChallengeAdminService challengeAdminService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @PostMapping("/create")
    @Operation(summary = "Create a new challenge")
    public ResponseEntity<ChallengeResponse> createChallenge(@RequestBody final CreateChallengeRequest challengeResponse,
                                                             @RequestHeader("Authorization") final String bearerToken) {
        return ResponseEntity.ok(challengeAdminService.createChallenge(challengeResponse, bearerToken));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @PutMapping("/update")
    public ResponseEntity<?> modifyChallenge(@RequestBody final ModifyChallengeRequest request) {
        log.info("modifyChallenge() - start: {}", request);
        var challenge = challengeAdminService.modifyChallenge(request);
        log.info("modifyChallenge() - end");
        return new ResponseEntity<>(challenge, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @PutMapping("/changeVisible")
    public ResponseEntity<?> changeVisibleChallenge(@RequestBody final VisibleChangeRequest request) {
        log.info("changeVisibleChallenge() - start: {}", request);
        var challenge = challengeAdminService.changeVisibleChallenge(request);
        log.info("changeVisibleChallenge() - end");
        return new ResponseEntity<>(challenge, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @DeleteMapping("/delete/{challengeId}")
    public ResponseEntity<?> deleteChallenge(@PathVariable("challengeId") final Long challengeId) {
        log.info("deleteChallenge() - start: {}", challengeId);
        challengeAdminService.deleteChallenge(challengeId);
        log.info("deleteChallenge() - end");
        return new ResponseEntity<>(challengeId, HttpStatus.OK);
    }
}
