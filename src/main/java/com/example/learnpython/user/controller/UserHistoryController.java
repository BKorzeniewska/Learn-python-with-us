package com.example.learnpython.user.controller;

import com.example.learnpython.user.service.UserHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/api/v1/user/history")
@RequiredArgsConstructor
public class UserHistoryController {

    private final UserHistoryService userHistoryService;


    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'MODERATOR', 'PRIVILEGED_USER')")
    @PostMapping("{articleId}")
    public ResponseEntity<?> saveHistory(@PathVariable("articleId") final Long articleId,
                                         @RequestHeader("Authorization") final String bearerToken) {
        log.info("saveHistory() - start, token = {}, articleId = {}", bearerToken, articleId);
        userHistoryService.saveHistory(bearerToken, articleId);
        log.info("saveHistory() - end");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'MODERATOR', 'PRIVILEGED_USER')")
    @DeleteMapping("/delete/{articleId}")
    public ResponseEntity<?> deleteHistory(@PathVariable("articleId") final Long articleId,
                                           @RequestHeader("Authorization") final String bearerToken) {
        log.info("deleteHistory() - start, token = {}, articleId = {}", bearerToken, articleId);
        userHistoryService.deleteHistory(bearerToken, articleId);
        log.info("deleteHistory() - end");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
