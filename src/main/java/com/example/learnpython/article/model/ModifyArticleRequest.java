package com.example.learnpython.article.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModifyArticleRequest {
    private Long id;
    private String title;
    private String content;
}
