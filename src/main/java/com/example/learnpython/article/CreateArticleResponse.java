package com.example.learnpython.article;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateArticleResponse {
    private Long id;
    private String title;
    private String content;
    private Long chapterId;
    private Long userId;
}
