package com.example.learnpython.article;

import java.time.LocalDate;
import java.util.List;

public interface ArticleService {
    ArticleResponse createArticle(CreateArticleRequest request);
    List<ArticleResponse> getArticlesByChapter(Long chapterId);
    ArticleResponse getArticleById(Long articleId);

    List<ArticleResponse> getArticlesByTitleContaining(String titleFragment);
    List<ArticleResponse> getArticlesByDateBetween(LocalDate startDate, LocalDate endDate);
    List<ArticleResponse> getArticlesByDate(LocalDate date);

}
