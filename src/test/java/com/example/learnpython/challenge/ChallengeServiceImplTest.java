package com.example.learnpython.challenge;

import com.example.learnpython.article.repository.ArticleRepository;
import com.example.learnpython.challenge.exception.ChallengeNotFoundException;
import com.example.learnpython.challenge.model.Type;
import com.example.learnpython.challenge.repository.ChallengeRepository;
import com.example.learnpython.challenge.service.ChallengeService;
import com.example.learnpython.challenge.service.ChallengeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class ChallengeServiceImplTest {

    @Mock
    private ChallengeRepository mockchallengeRepository;
    @Mock
    private ChallengeMapper challengeMapper;
    @Mock
    private ArticleRepository articleRepository;
    @Autowired
    private JsonConverter jsonConverter;

    private ChallengeService challengeServiceUnderTest;
    @BeforeEach
    void setUp() {
        challengeServiceUnderTest = new ChallengeServiceImpl(mockchallengeRepository,articleRepository, challengeMapper, jsonConverter);
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
   /*
    @Test
    public void getChallengeByNameTest() {
        final Challenge challenge = Challenge.builder()
                .id(1L)
                .name("name")
                .content(jsonConverter.convertToEntityAttribute(""))
                .question("question")
                .type(Type.OPEN)
                .visible(false)
                .exp(100L)
                .build();
        final List<Challenge> challengeTest = mockchallengeRepository.findByNameContaining("n").get();
        assertThat(challengeTest.get(0)).isEqualTo(challenge);

    }
    @Test
    public void getChallengesByArticleIdTest() {

        when(challengeRepository.findById(1L)).thenReturn(Optional.of(Challenge.builder()
                .id(1L)
                .name("Challenge 1")
                .build()));

    }
    @Test
    public void executePythonCodeChallengeTest() {

        when(challengeRepository.findById(1L)).thenReturn(Optional.of(Challenge.builder()
                .id(1L)
                .name("Challenge 1")
                .build()));

    }

    @Test
    public void executeOpenChallengeTest() {

        when(challengeRepository.findById(1L)).thenReturn(Optional.of(Challenge.builder()
                .id(1L)
                .name("Challenge 1")
                .build()));

    }
    @Test
    public void executeCloseChallengeTest() {

        when(challengeRepository.findById(1L)).thenReturn(Optional.of(Challenge.builder()
                .id(1L)
                .name("Challenge 1")
                .build()));

    }
*/
}