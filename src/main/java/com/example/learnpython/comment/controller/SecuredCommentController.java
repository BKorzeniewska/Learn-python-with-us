package com.example.learnpython.comment.controller;

import com.example.learnpython.comment.service.CommentService;
import com.example.learnpython.comment.model.CommentResponse;
import com.example.learnpython.comment.model.CreateCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/v1/comment")
@RequiredArgsConstructor
public class SecuredCommentController {

    private final CommentService commentService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'MODERATOR', 'PRIVILEGED_USER')")
    @PostMapping("/create")
    public ResponseEntity<CommentResponse> createComment(@RequestBody CreateCommentRequest createCommentRequest,
                                                         @RequestHeader("Authorization") final String bearerToken) {
        var comment = commentService.createComment(createCommentRequest, bearerToken);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }
}
