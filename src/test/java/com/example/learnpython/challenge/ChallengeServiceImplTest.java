package com.example.learnpython.challenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ChallengeServiceImplTest {

    @Mock
    private ChallengeRepository challengeRepository;
    @Mock
    private ChallengeMapper challengeMapper;
    @Autowired
    private JsonConverter jsonConverter;

    private ChallengeService challengeService;
    @BeforeEach
    void setUp() {
        challengeService = new ChallengeServiceImpl(challengeRepository, challengeMapper, jsonConverter);
    }

    @Test
    public void executePythonCodeTest() {

        when(challengeRepository.findById(1L)).thenReturn(Optional.of(Challenge.builder()
                .id(1L)
                .name("Challenge 1")
                .build()));

    }
}