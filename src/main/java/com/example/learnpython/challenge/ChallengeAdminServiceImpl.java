package com.example.learnpython.challenge;

import com.example.learnpython.article.Article;
import com.example.learnpython.article.ArticleRepository;
import com.example.learnpython.article.exception.ArticleNotFoundException;
import com.example.learnpython.challenge.exception.ChallengeNotFoundException;
import com.example.learnpython.challenge.model.ChallengeResponse;
import com.example.learnpython.challenge.model.CreateChallengeRequest;
import com.example.learnpython.challenge.model.ModifyChallengeRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ChallengeAdminServiceImpl  implements ChallengeAdminService {
    private final ChallengeRepository challengeRepository;
    private final ArticleRepository articleRepository;
    private final ChallengeMapper challengeMapper;
    private final JsonConverter jsonConverter;
    @Transactional
    @Override
    public ChallengeResponse createChallenge(CreateChallengeRequest request) {
        //var challenge = challengeMapper.toChallenge(request);
        Challenge challenge = Challenge.builder()
                .name(request.getName())
                .content(jsonConverter.convertToEntityAttribute(request.getContent()))
                .question(request.getQuestion())
                .type(request.getType())
                .build();
        List<Article> articles = new ArrayList<>();
        for (Long id : request.getArticlesID()) {
            final Article article = articleRepository
                    .findById(id)
                    .orElseThrow(() -> new ArticleNotFoundException(
                            "Article with provided ID not found", "ARTICLE_NOT_FOUND"));
            articles.add(article);

        }
        challenge.setArticles(articles);
        challengeRepository.save(challenge);
        for (Article article : articles) {
            Hibernate.initialize(article);
            Hibernate.initialize(article.getChallenges());
            article.getChallenges().add(challenge);
            articleRepository.save(article);
        }

        log.info("challenge: {}", challenge);

        return ChallengeResponse.builder()
                .id(challenge.getId())
                .name(challenge.getName())
                .question(challenge.getQuestion())
                .type(challenge.getType())
                .content(jsonConverter.convertToDatabaseColumn(challenge.getContent()))
                .articlesID((List<Long>) request.getArticlesID())
                .build();
    }


    @Override
    public ChallengeResponse modifyChallenge(ModifyChallengeRequest request) {
        if (request.getId() == null)
        {
            throw new  ChallengeNotFoundException("Challenge  ID cannot be null", "CHALLENGE_ID_NULL");
        }
        final Challenge challenge= challengeRepository.findById(request.getId())
                .orElseThrow(() -> new ChallengeNotFoundException("Challenge with provided ID not found", "CHALLENGE_NOT_FOUND"));
        if (request.getName()!= null)
        {
            challenge.setName(request.getName());
        }
        if (request.getQuestion()!= null)
        {
            challenge.setQuestion(request.getQuestion());
        }
        if (request.getType()!= null)
        {
            challenge.setType(request.getType());
        }
        if (request.getVisible()!= null)
        {
            challenge.setVisible(request.getVisible());
        }
        if (request.getContent()!= null)
        {
            challenge.setContent(jsonConverter.convertToEntityAttribute(request.getContent()));
        }
        if(request.getArticlesID()!= null)
        {
            List<Article> articles = new ArrayList<>();
            for (Long id : request.getArticlesID()) {
                final Article article = articleRepository
                        .findById(id)
                        .orElseThrow(() -> new ArticleNotFoundException(
                                "Article with provided ID not found", "ARTICLE_NOT_FOUND"));
                articles.add(article);

            }
            challenge.setArticles(articles);
        }
        challengeRepository.updateChallenge(challenge.getName(),challenge.getQuestion(), request.getContent(), challenge.getType(),challenge.getVisible(), challenge.getId());

        final Challenge updatedChallenge = challengeRepository.findById(request.getId())
                .orElseThrow(()->new ChallengeNotFoundException("Article with provided ID not found", "ARTICLE_NOT_FOUND"));
        return challengeMapper.toCreateChallengeResponse(updatedChallenge,jsonConverter);
    }

    @Override
    public void deleteChallenge(Long challengeId) {
        if(challengeId!= null)
        {
            throw new ChallengeNotFoundException("Challenge ID cannot be null", "CHALLENGE_ID_NULL");
        }
        challengeRepository.deleteChallengeById(challengeId);
    }

}
