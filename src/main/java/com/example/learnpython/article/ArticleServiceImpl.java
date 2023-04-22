package com.example.learnpython.article;

import com.example.learnpython.article.exception.ArticleNotFoundException;
import com.example.learnpython.chapter.ChapterRepository;
import com.example.learnpython.user.User;
import com.example.learnpython.article.exception.InvalidDateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ChapterRepository chapterRepository;
    private final ArticleMapper articleMapper;


    @Override
    public ArticleResponse createArticle(CreateArticleRequest request) {

        //validate user

        //validate chapter
        var chapter = chapterRepository.findById(request.getChapterId()).orElseThrow(
                () -> new ArticleNotFoundException("Provided ChapterID not found", "CHAPTER_NOT_FOUND"));

        //var article = articleMapper.toArticle(request);

        var article = Article.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .chapter(chapter)
                .user(User.builder().id(request.getUserId()).build())
                .build();

        articleRepository.save(article);

        log.info("article: {}", article);

        var articleResponse = ArticleResponse.builder()
                .id(article.getId())
                .chapterId(chapter.getId())
                .userId(request.getUserId())
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        return articleResponse;
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
