package com.example.learnpython.article.service;

import com.example.learnpython.article.model.ArticleResponse;
import com.example.learnpython.article.model.CreateArticleRequest;
import com.example.learnpython.article.model.ModifyArticleRequest;
import com.example.learnpython.article.model.VisibleChangeRequest;

public interface ArticleAdminService {

    ArticleResponse createArticle(final CreateArticleRequest request, final String bearerToken);
    ArticleResponse changeVisibleArticle(final VisibleChangeRequest request);

    ArticleResponse modifyArticle(final ModifyArticleRequest request);

    void deleteArticle(final Long articleId);
}
