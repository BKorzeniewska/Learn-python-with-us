package com.example.learnpython.challenge;

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
    @Query(value = "UPDATE challenge SET question = :question, content = :content, name = :name, type = :type, visible = :visible  WHERE challenge_id = :challengeId", nativeQuery = true)
    void updateChallenge(final String name, final String question, final String content, final Type type,final boolean visible, final Long challengeId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Challenge c WHERE c.id = :challengeId")
    void deleteChallengeById(final Long challengeId);
}
