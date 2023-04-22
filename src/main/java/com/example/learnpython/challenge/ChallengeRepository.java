package com.example.learnpython.challenge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    @Query("select c from Challenge c where c.name like %:nameFragment%")
    Optional<List<Challenge>> findByNameContaining(String nameFragment);
    @Query("SELECT c FROM Challenge c JOIN c.articles a WHERE a.id = :articleId")
    Optional<List<Challenge>> findByArticleId( Long articleId);


}
