package com.example.learnpython.article;

import com.example.learnpython.article.model.ArticleDTO;
import com.example.learnpython.article.model.ArticleResponse;
import com.example.learnpython.article.model.CreateArticleRequest;

import java.time.LocalDate;
import java.util.List;

public interface ArticleService {
    ArticleResponse createArticle(CreateArticleRequest request);
    ArticleDTO getArticlePageByChapter(final Long chapterId, final Integer pageNumber);
    List<ArticleResponse> getArticlesByChapter(final Long chapterId);
    ArticleResponse getArticleById(Long articleId);

    List<ArticleResponse> getArticlesByTitleContaining(String titleFragment);
    List<ArticleResponse> getArticlesByDateBetween(LocalDate startDate, LocalDate endDate);
    List<ArticleResponse> getArticlesByDate(LocalDate date);

}
