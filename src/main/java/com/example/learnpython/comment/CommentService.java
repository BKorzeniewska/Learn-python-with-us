package com.example.learnpython.comment;

import java.util.List;

public interface CommentService {
    CommentResponse createComment(CreateCommentRequest commentRequest);
    List<CommentResponse> getCommentsByArticleId(Long articleId);
}
