package com.example.learnpython.article;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a JOIN Chapter c ON (a.chapter.id = c.id) WHERE c.id = ?1 ORDER BY a.id ASC")
    Optional<List<Article>> findAllByChapterId(Long chapterId);

    @Transactional
    @Query("SELECT a FROM Article a JOIN Chapter c ON (a.chapter.id = c.id) WHERE c.id = ?1 ORDER BY a.id ASC")
    Stream<Article> findStreamAllByChapterId(Long chapterId);

    @Query("SELECT a FROM Article a WHERE a.title LIKE %:titleFragment%")
    Optional<List<Article>> findByTitleContaining(String titleFragment);

    @Query("SELECT a FROM Article a WHERE a.creationDate BETWEEN :startDate AND :endDate")
    Optional<List<Article>> findByCreationDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT a FROM Article a WHERE a.creationDate=:date")
    Optional<List<Article>> findByCreationDate(LocalDate date);

    @Transactional
    @Modifying
    @Query(value = "UPDATE article SET title = :title, content = :content, visible = :visible WHERE article_id = :articleId", nativeQuery = true)
    void updateArticle(final String title, final String content, final  boolean visible, final Long articleId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Article a WHERE a.id = :articleId")
    void deleteArticleById(final Long articleId);

}
