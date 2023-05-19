package com.example.learnpython.comment;

import com.example.learnpython.comment.model.CommentResponse;
import com.example.learnpython.comment.model.CreateCommentRequest;

import java.util.List;

public interface CommentService {
    CommentResponse createComment(final CreateCommentRequest commentRequest, final String bearerToken);
    List<CommentResponse> getCommentsByArticleId(Long articleId);
    List<CommentResponse> getCommentsByUserId(Long userId);
}
