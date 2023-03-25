package com.example.learnpython.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateArticleRequest {
    private String title;
    private String content;
    private Long chapterId;
    private Long userId;
}
