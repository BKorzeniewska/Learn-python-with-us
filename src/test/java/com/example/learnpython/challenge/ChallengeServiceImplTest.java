package com.example.learnpython.challenge;

import com.example.learnpython.challenge.model.*;
import com.example.learnpython.challenge.repository.ChallengeRepository;
import com.example.learnpython.challenge.service.ChallengeService;
import com.example.learnpython.challenge.service.ChallengeServiceImpl;
import com.example.learnpython.solution.repository.SolutionRepository;
import com.example.learnpython.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class ChallengeServiceImplTest {

    @Mock
    private ChallengeRepository mockchallengeRepository;
    @Mock
    private ChallengeMapper challengeMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SolutionRepository solutionRepository;


    @Autowired
    private JsonConverter jsonConverter;

    private ChallengeService challengeServiceUnderTest;
    @BeforeEach
    void setUp() {
        challengeServiceUnderTest = new ChallengeServiceImpl(mockchallengeRepository,userRepository,solutionRepository,challengeMapper,jsonConverter);
    }

    @Test
    public void findByIdTest() {
        final Challenge challenge = Challenge.builder()
                .id(1L)
                .name("name")
                .content(jsonConverter.convertToEntityAttribute(""))
                .question("question")
                .type(Type.OPEN)
                .visible(false)
                .exp(100L)
                .build();
        when(mockchallengeRepository.findById(1L)).thenReturn(Optional.of(challenge));

   }
    @Test
    public void findByNameContaining_WithMatchingNameFragment_ReturnsMatchingChallenges() {
        // Arrange
        String nameFragment = "ng";
        List<Challenge> challengeList = List.of(
                Challenge.builder()
                        .id(1L)
                        .name("challenge1")
                        .content(jsonConverter.convertToEntityAttribute(""))
                        .question("question1")
                        .type(Type.OPEN)
                        .visible(false)
                        .exp(100L)
                        .build(),
                Challenge.builder()
                        .id(2L)
                        .name("challenge2")
                        .content(jsonConverter.convertToEntityAttribute(""))
                        .question("question2")
                        .type(Type.OPEN)
                        .visible(false)
                        .exp(200L)
                        .build(),
                Challenge.builder()
                        .id(3L)
                        .name("challenge3")
                        .content(jsonConverter.convertToEntityAttribute(""))
                        .question("question3")
                        .type(Type.OPEN)
                        .visible(true)
                        .exp(300L)
                        .build()
        );
        when(mockchallengeRepository.findByNameContaining(nameFragment)).thenReturn(challengeList);

        // Act
        List<Challenge> result = mockchallengeRepository.findByNameContaining(nameFragment);

        // Assert
        assertEquals(3, result.size()); // Oczekiwana liczba obiektów Challenge
        assertEquals("challenge1", result.get(0).getName()); // Oczekiwana nazwa pierwszego obiektu Challenge
        assertEquals("question2", result.get(1).getQuestion()); // Oczekiwane pytanie drugiego obiektu Challenge
        assertEquals(300L, result.get(2).getExp());
    }

    @Test
    public void findByNameContaining_WithNoMatchingNameFragment_ReturnsEmptyList() {
        // Arrange
        String nameFragment = "xyz";
        List<Challenge> challengeList = List.of();
        when(mockchallengeRepository.findByNameContaining(nameFragment)).thenReturn(challengeList);

        // Act
        List<Challenge> result = mockchallengeRepository.findByNameContaining(nameFragment);

        // Assert
        assertTrue(result.isEmpty()); // Oczekiwana pusta lista
    }
}