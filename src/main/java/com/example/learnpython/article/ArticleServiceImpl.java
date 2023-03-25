package com.example.learnpython.article;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public CreateArticleResponse createArticle(CreateArticleRequest request) {

        //validate user

        //validate chapter



        return null;
    }

    @Override
    public List<CreateArticleResponse> getArticlesByChapter(Long chapterId) {
        return null;
    }

    @Override
    public CreateArticleResponse getArticleById(Long articleId) {
        return null;
    }
}
