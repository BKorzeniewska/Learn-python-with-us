package com.example.learnpython.article;

import java.util.List;

public interface ArticleService {
    ArticleResponse createArticle(CreateArticleRequest request);
    List<ArticleResponse> getArticlesByChapter(Long chapterId);
    ArticleResponse getArticleById(Long articleId);
}
