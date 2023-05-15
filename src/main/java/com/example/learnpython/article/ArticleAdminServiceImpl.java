package com.example.learnpython.article;

import com.example.learnpython.article.exception.ArticleNotFoundException;
import com.example.learnpython.article.model.ArticleResponse;
import com.example.learnpython.article.model.CreateArticleRequest;
import com.example.learnpython.article.model.ModifyArticleRequest;
import com.example.learnpython.chapter.ChapterRepository;
import com.example.learnpython.user.User;
import com.example.learnpython.user.UserRepository;
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

    @Override
    public ArticleResponse createArticle(final CreateArticleRequest request, final String bearerToken) {

        //validate user
        final String token = bearerToken.substring(7);
        final User user = userRepository.findByToken(token)
                .orElseThrow(() -> new ArticleNotFoundException("User with provided token not found", "USER_NOT_FOUND"));

        //validate chapter
        var chapter = chapterRepository.findById(request.getChapterId()).orElseThrow(
                () -> new ArticleNotFoundException("Provided ChapterID not found", "CHAPTER_NOT_FOUND"));

        var article = Article.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .chapter(chapter)
                .creationDate(LocalDate.now())
                .user(user)
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

        //articleRepository.save(article);
        articleRepository.updateArticle(article.getTitle(), article.getContent(), article.getId());

        final Article updatedArticle = articleRepository.findById(request.getId())
                .orElseThrow(() -> new ArticleNotFoundException("Article with provided ID not found", "ARTICLE_NOT_FOUND"));

        return articleMapper.toCreateArticleResponse(updatedArticle);
    }
}
