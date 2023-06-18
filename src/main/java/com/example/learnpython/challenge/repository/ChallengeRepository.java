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
    @Query("""
            SELECT c FROM Challenge c
            WHERE c.name LIKE %:nameFragment%
            AND (c.visible = true OR (c.visible = false AND :userId IS NOT NULL AND c.user.id = :userId))
            """)
    List<Challenge> findByNameContaining(final String nameFragment, final Long userId);

    @Query("SELECT c FROM Challenge c WHERE c.name LIKE %:nameFragment%")
    List<Challenge> findByNameContaining(final String nameFragment);

    @Query("""
            SELECT c FROM Challenge c
            JOIN c.articles a
            WHERE a.id = :articleId
            AND (c.visible = true OR (c.visible = false AND :userId IS NOT NULL AND c.user.id = :userId))
            """)
    List<Challenge> findByArticleId(final Long articleId, final Long userId);

    @Query("SELECT c FROM Challenge c JOIN c.articles a WHERE a.id = :articleId")
    List<Challenge> findByArticleId(final Long articleId);

    @Query("""
            SELECT c FROM Challenge c
            WHERE c.visible = true OR (c.visible = false AND :userId IS NOT NULL AND c.user.id = :userId)
            """)
    List<Challenge> findAllChallenges(final Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Challenge c WHERE c.id = :challengeId")
    void deleteById(final Long challengeId);

    //update user to null when user is deleted
    @Transactional
    @Modifying
    @Query("UPDATE Challenge c SET c.user = null WHERE c.user.id = :userId")
    int updateUserToNull(final Long userId);

}
