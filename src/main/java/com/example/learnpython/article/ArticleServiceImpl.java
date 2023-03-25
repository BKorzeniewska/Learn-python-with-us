package com.example.learnpython.article;

import com.example.learnpython.article.exception.ArticleNotFoundException;
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
    public ArticleResponse createArticle(CreateArticleRequest request) {

        //validate user

        //validate chapter

        var article = articleMapper.toArticle(request);

        log.info("article: {}", article);

        articleRepository.save(article);

        return articleMapper.toCreateArticleResponse(article);
    }

    @Override
    public List<ArticleResponse> getArticlesByChapter(Long chapterId) {
        var articles = articleRepository
                .findByChapterId(chapterId)
                .orElseThrow(() -> new ArticleNotFoundException("Articles with provided ChapterID not found"));

        return articles.stream()
                .map(articleMapper::toCreateArticleResponse)
                .toList();
    }

    @Override
    public ArticleResponse getArticleById(Long articleId) {
        var article = articleRepository
                .findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("Article with provided ID not found"));
        return articleMapper.toCreateArticleResponse(article);
    }
}