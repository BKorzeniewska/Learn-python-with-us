package com.example.learnpython.article;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuArticleResponse {
    private long id;
    private String title;
}
