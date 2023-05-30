package com.example.learnpython.challenge;

import com.example.learnpython.article.model.ModifyArticleRequest;
import com.example.learnpython.challenge.model.ChallengeResponse;
import com.example.learnpython.challenge.model.CreateChallengeRequest;
import com.example.learnpython.challenge.model.ModifyChallengeRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/v1/challenge")
@RequiredArgsConstructor
@Log4j2
public class ChallengeAdminController {
    private final ChallengeAdminService challengeAdminService;
    @PostMapping("/create")
    @Operation(summary = "Create a new challenge")
    public ResponseEntity<ChallengeResponse> createChallenge(@RequestBody CreateChallengeRequest challengeResponse) {
        return ResponseEntity.ok(challengeAdminService.createChallenge(challengeResponse));
    }

    @PutMapping("/update")
    public ResponseEntity<?> modifyChallenge(@RequestBody final ModifyChallengeRequest request) {
        log.info("modifyChallenge() - start: {}", request);
        var challenge = challengeAdminService.modifyChallenge(request);
        log.info("modifyChallenge() - end");
        return new ResponseEntity<>(challenge, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{challengeId}")
    public ResponseEntity<?> deleteChallenge(@PathVariable("articleId") final Long challengeId) {
        log.info("deleteChallenge() - start: {}", challengeId);
        challengeAdminService.deleteChallenge(challengeId);
        log.info("deleteChallenge() - end");
        return new ResponseEntity<>(challengeId, HttpStatus.OK);
    }
}
