package com.example.learnpython.article;

import com.example.learnpython.article.model.Article;
import com.example.learnpython.article.model.ArticleDTO;
import com.example.learnpython.article.model.ArticleResponse;
import com.example.learnpython.article.repository.ArticleRepository;
import com.example.learnpython.article.service.ArticleServiceImpl;
import com.example.learnpython.chapter.model.Chapter;
import com.example.learnpython.chapter.repository.ChapterRepository;
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
        articleServiceImplUnderTest = new ArticleServiceImpl(mockArticleRepository, articleMapper);
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
    @Test
    void getArticlesByChapterTest() {
        Long chapterId = 1L;
        Article article1 = Article.builder()
                .id(1L)
                .title("Article 1")
                .content("Content 1")
                .visible(true)
                .chapter(Chapter.builder().id(chapterId).build())
                .build();

        Article article2 = Article.builder()
                .id(2L)
                .title("Article 2")
                .content("Content 2")
                .visible(false)
                .chapter(Chapter.builder().id(chapterId).build())
                .build();

        List<Article> articles = List.of(article1, article2);

        when(mockArticleRepository.findAllByChapterId(chapterId))
                .thenReturn(Optional.of(articles));


        List<ArticleResponse> expectedResponses = articles.stream()
                .filter(Article::getVisible)
                .map(articleMapper::toCreateArticleResponse)
                .toList();

        List<ArticleResponse> result = articleServiceImplUnderTest.getArticlesByChapter(chapterId);
        assertThat(result).isEqualTo(expectedResponses);
    }

    @Test
    void getArticlesByTitleContainingTest() {
        String titleFragment = "example";
        Article article1 = Article.builder()
                .id(1L)
                .title("Example Article")
                .content("Content 1")
                .chapter(Chapter.builder().id(1L).build())
                .build();

        Article article2 = Article.builder()
                .id(2L)
                .title("Another Article")
                .content("Content 2")
                .chapter(Chapter.builder().id(2L).build())
                .build();

        List<Article> articles = List.of(article1, article2);
        when(mockArticleRepository.findByTitleContaining(titleFragment))
                .thenReturn(Optional.of(articles));

        List<ArticleResponse> expectedResponses = articles.stream()
                .map(articleMapper::toCreateArticleResponse)
                .toList();

        List<ArticleResponse> result = articleServiceImplUnderTest.getArticlesByTitleContaining(titleFragment);
        assertThat(result).isEqualTo(expectedResponses);
    }
}