package com.example.learnpython.comment.controller;

import com.example.learnpython.comment.service.CommentAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/v1/comment")
@RequiredArgsConstructor
@Log4j2
public class CommentAdminController {
    private final CommentAdminService commentAdminService;
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId) {
        log.info("deleteComment() - start: {}", commentId);
        commentAdminService.deleteComment(commentId);
        log.info("deleteComment() - end");
        return new ResponseEntity<>(commentId, HttpStatus.OK);
    }

}
