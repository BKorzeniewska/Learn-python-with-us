package com.example.learnpython.chapter.model;

import com.example.learnpython.article.model.MenuArticleResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MenuChapterResponse {
    private Long id;
    private String name;
    private List<MenuArticleResponse> articles;
}
