package com.example.learnpython.article;

import com.example.learnpython.article.model.ArticleDTO;
import com.example.learnpython.article.model.ArticleResponse;
import com.example.learnpython.chapter.Chapter;
import com.example.learnpython.chapter.ChapterRepository;
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
class ArticleServiceImplTest {

    @Mock
    private ArticleRepository mockArticleRepository;
    @Mock
    private ChapterRepository chapterRepository;
    @Autowired
    private ArticleMapper articleMapper;
    private ArticleServiceImpl articleServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        articleServiceImplUnderTest = new ArticleServiceImpl(mockArticleRepository, chapterRepository, articleMapper);
    }

    @Test
    void getArticlePageByChapter() {
        // given
        final Article article = Article.builder()
                .id(1L)
                .title("title")
                .content("content")
                .chapter(Chapter.builder().id(1L).build())
                .build();

        final ArticleDTO articleDTO = ArticleDTO.builder()
                .article(ArticleResponse.builder()
                        .id(article.getId())
                        .title(article.getTitle())
                        .content(article.getContent())
                        .chapterId(article.getChapter().getId())
                        .build())
                .currentArticle(1L)
                .nextArticleIndex(null)
                .previousArticleIndex(null)
                .totalArticles(1L)
                .build();

        //when
        when(mockArticleRepository.findById(1L)).thenReturn(Optional.of(article));
        when(mockArticleRepository.findAllByChapterId(article.getChapter().getId())).thenReturn(Optional.of(List.of(article)));

        // then
        final ArticleDTO articleDTOResult = articleServiceImplUnderTest.getArticlePageByChapter(1L);
        assertThat(articleDTOResult).isEqualTo(articleDTO);
    }
}