package com.example.learnpython.article.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateArticleRequest {
    private String title;
    private String content;
    private Boolean visible;
    private Long chapterId;
}
