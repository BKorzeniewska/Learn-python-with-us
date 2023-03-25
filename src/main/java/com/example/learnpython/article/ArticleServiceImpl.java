package com.example.learnpython.article;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;


    @Override
    public CreateArticleResponse createArticle(CreateArticleRequest request) {

        //validate user

        //validate chapter

        var article = articleMapper.toArticle(request);

        log.info("article: {}", article);

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
