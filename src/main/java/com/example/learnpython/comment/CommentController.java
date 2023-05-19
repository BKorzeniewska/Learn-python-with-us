package com.example.learnpython.comment;

import com.example.learnpython.comment.model.CommentResponse;
import com.example.learnpython.comment.model.CreateCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentResponse> createComment(@RequestBody CreateCommentRequest createCommentRequest,
                                                         @RequestHeader("Authorization") final String bearerToken) {
        var comment = commentService.createComment(createCommentRequest, bearerToken);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByArticleId(@PathVariable("articleId") Long articleId) {
        var comments = commentService.getCommentsByArticleId(articleId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByUserId(@PathVariable("userId") Long userId) {
        var comments = commentService.getCommentsByUserId(userId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
