package com.example.learnpython.article.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ArticleResponse {
    private Long id;
    private String title;
    private String content;
    private Long chapterId;
    private Boolean visible;
    private Long userId;
    private LocalDate date;
}
