package com.example.learnpython.article;

import com.example.learnpython.article.model.ArticleResponse;
import com.example.learnpython.article.model.CreateArticleRequest;
import com.example.learnpython.article.model.ModifyArticleRequest;

public interface ArticleAdminService {

    ArticleResponse createArticle(final CreateArticleRequest request, final String bearerToken);

    ArticleResponse modifyArticle(final ModifyArticleRequest request);
}
