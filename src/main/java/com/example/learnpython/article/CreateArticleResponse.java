package com.example.learnpython.article;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateArticleResponse {
    private Long articleId;
    private String title;
    private String content;
    private Long chapterId;
    private Long userId;
}
