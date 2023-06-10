package com.example.learnpython.challenge;

import com.example.learnpython.article.repository.ArticleRepository;
import com.example.learnpython.challenge.repository.ChallengeRepository;
import com.example.learnpython.challenge.service.ChallengeService;
import com.example.learnpython.challenge.service.ChallengeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class ChallengeServiceImplTest {

    @Mock
    private ChallengeRepository challengeRepository;
    @Mock
    private ChallengeMapper challengeMapper;
    @Mock
    private ArticleRepository articleRepository;
    @Autowired
    private JsonConverter jsonConverter;

    private ChallengeService challengeService;
    @BeforeEach
    void setUp() {
        challengeService = new ChallengeServiceImpl(challengeRepository,articleRepository, challengeMapper, jsonConverter);
    }

    @Test
    public void executePythonCodeTest() {

        when(challengeRepository.findById(1L)).thenReturn(Optional.of(Challenge.builder()
                .id(1L)
                .name("Challenge 1")
                .build()));

    }
}