package com.example.learnpython.solution.controller;

import com.example.learnpython.solution.service.SolutionService;
import com.example.learnpython.solution.model.SolutionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/solution")
@RequiredArgsConstructor
@Log4j2
public class SolutionController {
    private final SolutionService solutionService;

    @PostMapping("/add")
    public ResponseEntity<?> addUserSolution(@RequestBody SolutionRequest solutionRequest) {

        log.info("addUserSolution - start, solutionRequest: {}", solutionRequest);
        final long solutionId = solutionService.addUserSolution(solutionRequest);
        log.info("addUserSolution - end, solutionId: {}", solutionId);
        return ResponseEntity.ok(solutionId);
    }
}
