package com.example.learnpython.comment;

import com.example.learnpython.comment.model.CommentResponse;
import com.example.learnpython.comment.model.CreateCommentRequest;

import java.util.List;

public interface CommentService {
    CommentResponse createComment(CreateCommentRequest commentRequest);
    List<CommentResponse> getCommentsByArticleId(Long articleId);
    List<CommentResponse> getCommentsByUserId(Long userId);
}
