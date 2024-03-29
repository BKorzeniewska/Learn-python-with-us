package com.example.learnpython.article.service;

import com.example.learnpython.article.model.*;
import com.example.learnpython.article.ArticleMapper;
import com.example.learnpython.article.exception.ArticleNotFoundException;
import com.example.learnpython.article.model.ArticleResponse;
import com.example.learnpython.article.model.CreateArticleRequest;
import com.example.learnpython.article.model.ModifyArticleRequest;
import com.example.learnpython.article.repository.ArticleRepository;
import com.example.learnpython.chapter.repository.ChapterRepository;
import com.example.learnpython.comment.repository.CommentRepository;
import com.example.learnpython.user.model.entity.User;
import com.example.learnpython.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Log4j2
@RequiredArgsConstructor
public class ArticleAdminServiceImpl implements ArticleAdminService {

    private final ArticleRepository articleRepository;
    private final ChapterRepository chapterRepository;
    private final UserRepository userRepository;
    private final ArticleMapper articleMapper;
    private final CommentRepository commentRepository;

    @Override
    public ArticleResponse createArticle(final CreateArticleRequest request, final String bearerToken) {

        //validate user
        final String token = bearerToken.substring(7);
        final User user = userRepository.findByToken(token)
                .orElseThrow(() -> new ArticleNotFoundException("User with provided token not found", "USER_NOT_FOUND"));


        if (request.getTitle() == null || request.getTitle().isEmpty()) {
            throw new ArticleNotFoundException("Article title cannot be null or empty", "ARTICLE_TITLE_EMPTY");
        }

        if (request.getContent() == null || request.getContent().isEmpty()) {
            throw new ArticleNotFoundException("Article content cannot be null or empty", "ARTICLE_CONTENT_EMPTY");
        }

        //validate chapter
        var chapter = chapterRepository.findById(request.getChapterId()).orElseThrow(
                () -> new ArticleNotFoundException("Provided ChapterID not found", "CHAPTER_NOT_FOUND"));

        var article = Article.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .chapter(chapter)
                .creationDate(LocalDate.now())
                .user(user)
                .visible(false)
                .build();

        articleRepository.save(article);

        log.info("article: {}", article.getTitle());

        return articleMapper.toCreateArticleResponse(article);
    }

    @Override
    @Transactional
    public ArticleResponse modifyArticle(final ModifyArticleRequest request) {

        if (request.getId() == null) {
            throw new ArticleNotFoundException("Article ID cannot be null", "ARTICLE_ID_NULL");
        }

        final Article article = articleRepository.findById(request.getId())
                .orElseThrow(() -> new ArticleNotFoundException("Article with provided ID not found", "ARTICLE_NOT_FOUND"));

        log.info("Updating article with id: {}", article.getId());

        if (request.getTitle() != null) {
            article.setTitle(request.getTitle());
        }

        if (request.getContent() != null) {
            article.setContent(request.getContent());
        }
        if (request.getVisible() != null) {
            article.setVisible(request.getVisible());
        }

        articleRepository.updateArticle(article.getTitle(), article.getContent(), article.getVisible(), article.getId());

        final Article updatedArticle = articleRepository.findById(request.getId())
                .orElseThrow(() -> new ArticleNotFoundException("Article with provided ID not found", "ARTICLE_NOT_FOUND"));

        return articleMapper.toCreateArticleResponse(updatedArticle);
    }

    @Override
    @Transactional
    public ArticleResponse changeVisibleArticle(final VisibleChangeRequest request) {

        if (request.getArticleId() == null) {
            throw new ArticleNotFoundException("Article ID cannot be null", "ARTICLE_ID_NULL");
        }

        final Article article = articleRepository.findById(request.getArticleId())
                .orElseThrow(() -> new ArticleNotFoundException("Article with provided ID not found", "ARTICLE_NOT_FOUND"));

        if (request.getVisible() != null) {
            article.setVisible(request.getVisible());
        }

        articleRepository.updateArticle(article.getTitle(), article.getContent(), article.getVisible(), article.getId());

        final Article updatedArticle = articleRepository.findById(request.getArticleId())
                .orElseThrow(() -> new ArticleNotFoundException("Article with provided ID not found", "ARTICLE_NOT_FOUND"));

        return articleMapper.toCreateArticleResponse(updatedArticle);
    }


    @Override
    public void deleteArticle(final Long articleId) {
        if (articleId == null) {
            throw new ArticleNotFoundException("Article ID cannot be null", "ARTICLE_ID_NULL");
        }

        final Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("Article with provided ID not found", "ARTICLE_NOT_FOUND"));
        article.setComments(null);

        int rows = commentRepository.updateArticleToNull(articleId);
        log.info("Updated {} rows", rows);

        articleRepository.deleteArticleById(articleId);
    }
}
