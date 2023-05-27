package com.example.learnpython.user.repository;

import com.example.learnpython.user.model.entity.UserHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM UserHistory uh WHERE :token IN (uh.user.tokens) AND uh.article.id=:articleId")
    void deleteByUserTokenAndArticleId(final String token, final Long articleId);
}
