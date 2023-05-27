package com.example.learnpython.user.service;

import com.example.learnpython.article.Article;
import com.example.learnpython.article.ArticleRepository;
import com.example.learnpython.article.exception.ArticleNotFoundException;
import com.example.learnpython.comment.exception.CommentIllegalStateException;
import com.example.learnpython.user.exception.UserNotFoundException;
import com.example.learnpython.user.model.entity.User;
import com.example.learnpython.user.model.entity.UserHistory;
import com.example.learnpython.user.repository.UserHistoryRepository;
import com.example.learnpython.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserHistoryServiceImpl implements UserHistoryService {

    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;

    private final UserHistoryRepository userHistoryRepository;


    @Override
    public void saveHistory(final String bearerToken, final Long articleId) {

        log.info("saveHistory() - start, articleId = {}", articleId);

        if (bearerToken == null || bearerToken.isBlank()) {
            throw new CommentIllegalStateException("Bearer token cannot be blank", "BEARER_TOKEN_BLANK");
        }

        final String token = bearerToken.substring(7);
        final User user = userRepository.findByToken(token)
                .orElseThrow(() -> new UserNotFoundException("User with provided token not found", "USER_NOT_FOUND"));

        final Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("Article with provided id not found", "ARTICLE_NOT_FOUND"));

        log.info("saveHistory() - user = {}, article = {}", user.getId(), article);

        final UserHistory userHistory = UserHistory.builder()
                .user(user)
                .article(article)
                .build();

        userHistoryRepository.save(userHistory);
    }

    @Override
    public void deleteHistory(final String bearerToken, final Long articleId) {
        //TODO

        if (bearerToken == null || bearerToken.isBlank()) {
            throw new CommentIllegalStateException("Bearer token cannot be blank", "BEARER_TOKEN_BLANK");
        }

        final String token = bearerToken.substring(7);

        userHistoryRepository.deleteByUserTokenAndArticleId(token, articleId);
    }
}
