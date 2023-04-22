package com.example.learnpython.challenge;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin/v1/challenge")
@RequiredArgsConstructor
public class ChallengeAdminController {
    private final ChallengeService challengeService;
    @PostMapping("/create")
    @Operation(summary = "Create a new challenge")
    public ResponseEntity<ChallengeResponse> createChallenge(@RequestParam CreateChallengeRequest challengeResponse) {
        return ResponseEntity.ok(challengeService.createChallenge(challengeResponse));
    }
}
