package com.example.learnpython.chapter;

import com.example.learnpython.article.MenuArticleResponse;
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
