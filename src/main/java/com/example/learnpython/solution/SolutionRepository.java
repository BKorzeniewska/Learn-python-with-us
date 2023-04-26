package com.example.learnpython.solution;

import com.example.learnpython.solution.exception.SolutionsCountPerChallengeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {
    @Query("SELECT s FROM Solution s WHERE s.user.id = ?1 AND s.challenge.id = ?2")
    Solution findByUserIdAndChallengeId(long userId, long challengeId);

    @Query(value = "SELECT count(s) as count, s.challenge.id as challengeId FROM Solution s GROUP BY s.challenge.id")
    SolutionsCountPerChallengeProjection findSolutionsCountPerChallenge();
}
