package com.example.learnpython.solution.repository;

import com.example.learnpython.solution.model.Solution;
import com.example.learnpython.solution.model.SolutionsCountPerChallengeProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {
    @Query("SELECT s FROM Solution s WHERE s.user.id = ?1 AND s.challenge.id = ?2")
    Optional<Solution> findByUserIdAndChallengeId(long userId, long challengeId);

    @Query(value = "SELECT count(s) as count, s.challenge.id as challengeId FROM Solution s GROUP BY s.challenge.id")
    SolutionsCountPerChallengeProjection findSolutionsCountPerChallenge();

    @Query("SELECT s FROM Solution s WHERE s.user.id = ?1")
    List<Solution> findByUserId(final long userId);
    @Transactional
    @Modifying
    @Query("DELETE FROM Solution s WHERE s.id = :solutionId")
    void deleteSolutionById(final Long solutionId);

    @Query("SELECT s FROM Solution s WHERE s.challenge.id = ?1")
    List<Solution> findByChallengeId(final long challengeId);

}
