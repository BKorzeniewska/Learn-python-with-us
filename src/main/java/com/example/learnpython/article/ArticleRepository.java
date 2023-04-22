package com.example.learnpython.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a JOIN Chapter c ON (a.chapter.id = c.id) WHERE c.id = ?1")
    Optional<List<Article>> findByChapterId(Long chapterId);

    @Query("select a from Article a where a.title like %:titleFragment%")
    Optional<List<Article>> findByTitleContaining(String titleFragment);
    @Query("select a from Article a where a.creationDate between :startDate and :endDate")
    Optional<List<Article>> findByCreationDateBetween(LocalDate startDate, LocalDate endDate);
    @Query("select a from Article a where a.creationDate=:date")
    Optional<List<Article>> findByCreationDate(LocalDate date);

}
