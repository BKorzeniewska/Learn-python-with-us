package com.example.learnpython.comment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCommentRequest {
    private String content;
    private Long articleId;
    private Long userId;
}