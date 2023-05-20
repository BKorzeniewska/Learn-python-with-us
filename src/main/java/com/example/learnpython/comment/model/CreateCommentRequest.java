package com.example.learnpython.comment.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCommentRequest {
    private String content;
    private Long articleId;
}
