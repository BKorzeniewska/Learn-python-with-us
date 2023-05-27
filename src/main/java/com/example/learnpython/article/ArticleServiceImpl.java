package com.example.learnpython.article;

import com.example.learnpython.article.exception.ArticleNotFoundException;
import com.example.learnpython.article.model.ArticleDTO;
import com.example.learnpython.article.model.ArticleResponse;
import com.example.learnpython.article.exception.InvalidDateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Override
    public ArticleDTO getArticlePageByChapter(final Long articleId) {

        log.info("getArticlePageByChapter - start, articleId: {}", articleId);

        final Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("Article with provided ID not found", "ARTICLE_NOT_FOUND"));

        final List<Article> articles = articleRepository.findAllByChapterId(article.getChapter().getId())
                .orElseThrow(() -> new ArticleNotFoundException("Articles with provided ChapterID not found", "ARTICLES_NOT_FOUND"));

        final int articleListIndex = articles.stream()
                .filter(a -> a.getId() == articleId)
                .findFirst()
                .map(articles::indexOf)
                .orElse(-1);

        log.info("getArticlePageByChapter - articleListIndex: {}", articleListIndex);

        final Long previousArticleIndex = articleListIndex > 0 ? articles.get(articleListIndex - 1).getId() : null;
        final Long nextArticleIndex = articleListIndex < articles.size() -1 ? articles.get(articleListIndex + 1).getId() : null;
        final Long totalElements = (long) articles.size();
        final Long currentPage = (long) (articleListIndex + 1);
        final ArticleResponse articleResponse = articleMapper.toCreateArticleResponse(article);

        return new ArticleDTO(articleResponse, previousArticleIndex, nextArticleIndex, totalElements, currentPage);
    }

    @Override
    public List<ArticleResponse> getArticlesByChapter(final Long chapterId) {
        final List<Article> articles = articleRepository
                .findAllByChapterId(chapterId)
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
    public List<ArticleResponse> getArticlesByTitleContaining(String titleFragment) {
        var articles = articleRepository
                .findByTitleContaining(titleFragment)
                .orElseThrow(() -> new ArticleNotFoundException(
                        "Articles with provided title's fragment not found", "ARTICLES_NOT_FOUND"));

        return articles.stream()
                .map(articleMapper::toCreateArticleResponse)
                .toList();
    }

    @Override
    public List<ArticleResponse> getArticlesByDateBetween(LocalDate startDate, LocalDate endDate) {
        if(startDate.isAfter(endDate)) throw new InvalidDateException("End data is earlier than start date","INVALID_DATE");
        var articles = articleRepository
                .findByCreationDateBetween(startDate,endDate)
                .orElseThrow(() -> new ArticleNotFoundException(
                        "Articles with provided timestamp between"+startDate +" and "+ endDate +"  not found", "ARTICLES_NOT_FOUND"));

        return articles.stream()
                .map(articleMapper::toCreateArticleResponse)
                .toList();
    }

    @Override
    public List<ArticleResponse> getArticlesByDate(LocalDate date) {
        var articles = articleRepository
                .findByCreationDate(date)
                .orElseThrow(() -> new ArticleNotFoundException(
                        "Articles with provided date not found", "ARTICLES_NOT_FOUND"));

        return articles.stream()
                .map(articleMapper::toCreateArticleResponse)
                .toList();
    }
}
