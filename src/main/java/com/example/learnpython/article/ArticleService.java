package com.example.learnpython.article;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ArticleService {
    ArticleResponse createArticle(CreateArticleRequest request);
    List<ArticleResponse> getArticlesByChapter(Long chapterId);
    ArticleResponse getArticleById(Long articleId);

    List<ArticleResponse> getByTitleContaining(String titleFragment);
    List<ArticleResponse> getByTimestampBetween(LocalDate startDate, LocalDate endDate);
    List<ArticleResponse> getByTimestamp(LocalDate date);

}
