package com.example.learnpython.challenge;

import com.example.learnpython.challenge.model.ChallengeResponse;
import com.example.learnpython.challenge.model.CreateChallengeRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/v1/challenge")
@RequiredArgsConstructor
public class ChallengeAdminController {
    private final ChallengeService challengeService;
    @PostMapping("/create")
    @Operation(summary = "Create a new challenge")
    public ResponseEntity<ChallengeResponse> createChallenge(@RequestBody CreateChallengeRequest challengeResponse) {
        return ResponseEntity.ok(challengeService.createChallenge(challengeResponse));
    }
}
