package com.example.learnpython.article.service;

import com.example.learnpython.article.model.*;

import java.time.LocalDate;
import java.util.List;

public interface ArticleService {

    ArticleDTO getArticlePageByChapter(final Long articleId);

    List<ArticleResponse> getArticlesByChapter(final Long chapterId);

    ArticleResponse getArticleById(Long articleId);

    List<ArticleResponse> getArticlesByTitleContaining(String titleFragment);

    List<ArticleResponse> getArticlesByDateBetween(LocalDate startDate, LocalDate endDate);

    List<ArticleResponse> getArticlesByDate(LocalDate date);

    List<MenuArticleResponse> getNewestArticle();

}
