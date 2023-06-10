package com.example.learnpython.challenge.repository;

import com.example.learnpython.challenge.Challenge;
import com.example.learnpython.challenge.model.Type;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    @Query("SELECT c FROM Challenge c WHERE c.name LIKE %:nameFragment%")
    Optional<List<Challenge>> findByNameContaining(String nameFragment);
    @Query("SELECT c FROM Challenge c JOIN c.articles a WHERE a.id = :articleId")
    Optional<List<Challenge>> findByArticleId( Long articleId);

    @Transactional
    @Modifying
    void deleteById(Long challengeId);
}
