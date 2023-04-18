package com.example.learnpython.article;

import com.example.learnpython.article.exception.ArticleNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
                .orElseThrow(() -> new ArticleNotFoundException(
                        "Articles with provided ChapterID not found", "ARTICLES_NOT_FOUND"));

        return articles.stream()
                .map(articleMapper::toCreateArticleResponse)
                .toList();
    }

    @Override
    public ArticleResponse getArticleById(Long articleId) {
        var article = articleRepository
                .findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(
                        "Article with provided ID not found", "ARTICLE_NOT_FOUND"));
        return articleMapper.toCreateArticleResponse(article);
    }

    @Override
    public List<ArticleResponse> getByTitleContaining(String titleFragment) {
        var articles = articleRepository
                .findByTitleContaining(titleFragment)
                .orElseThrow(() -> new ArticleNotFoundException(
                        "Articles with provided title's fragment not found", "ARTICLES_NOT_FOUND"));

        return articles.stream()
                .map(articleMapper::toCreateArticleResponse)
                .toList();
    }

    @Override
    public List<ArticleResponse> getByDateBetween(LocalDate startDate, LocalDate endDate) {
        var articles = articleRepository
                .findByDateBetween(startDate,endDate)
                .orElseThrow(() -> new ArticleNotFoundException(
                        "Articles with provided timestamp between"+startDate.toString()+" and "+ endDate.toString()+"  not found", "ARTICLES_NOT_FOUND"));

        return articles.stream()
                .map(articleMapper::toCreateArticleResponse)
                .toList();
    }

    @Override
    public List<ArticleResponse> getByDate(LocalDate date) {
        var articles = articleRepository
                .findByDate(date)
                .orElseThrow(() -> new ArticleNotFoundException(
                        "Articles with provided date not found", "ARTICLES_NOT_FOUND"));

        return articles.stream()
                .map(articleMapper::toCreateArticleResponse)
                .toList();
    }
}
