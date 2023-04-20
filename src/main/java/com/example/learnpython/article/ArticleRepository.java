package com.example.learnpython.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<List<Article>> findByChapterId(Long chapterId);

    @Query("SELECT a FROM Article a WHERE a.title LIKE %:titleFragment%")
    Optional<List<Article>> findByTitleContaining(String titleFragment);
    @Query("SELECT a FROM Article a WHERE a.creationDate BETWEEN :startDate AND :endDate")
    Optional<List<Article>> findByCreationDateBetween(LocalDate startDate, LocalDate endDate);
    @Query("SELECT a FROM Article a WHERE a.creationDate=:date")
    Optional<List<Article>> findByCreationDate(LocalDate date);

}
