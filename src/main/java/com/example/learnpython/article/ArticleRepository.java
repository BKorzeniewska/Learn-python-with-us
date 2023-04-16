package com.example.learnpython.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<List<Article>> findByChapterId(Long chapterId);
    Optional<List<Article>> findByTitleContaining(String titleFragment);
    Optional<List<Article>> findByTimestampBetween(LocalDate startDate, LocalDate endDate);
    Optional<List<Article>> findByTimestamp(LocalDate date);

}
