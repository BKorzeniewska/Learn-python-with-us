package com.example.learnpython.article;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ArticleService {
    ArticleResponse createArticle(CreateArticleRequest request);
    List<ArticleResponse> getArticlesByChapter(Long chapterId);
    ArticleResponse getArticleById(Long articleId);

    List<ArticleResponse> getByTitleContaining(String titleFragment);
    List<ArticleResponse> getByDateBetween(LocalDate startDate, LocalDate endDate);
    List<ArticleResponse> getByDate(LocalDate date);

}
