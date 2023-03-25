package com.example.learnpython.article;

import java.util.List;

public interface ArticleService {
    CreateArticleResponse createArticle(CreateArticleRequest request);
    List<CreateArticleResponse> getArticlesByChapter(Long chapterId);
    CreateArticleResponse getArticleById(Long articleId);
}
