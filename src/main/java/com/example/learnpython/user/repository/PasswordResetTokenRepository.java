package com.example.learnpython.user.repository;

import com.example.learnpython.user.model.entity.PasswordResetToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(final String token);

    @Modifying
    @Transactional
    @Query("UPDATE PasswordResetToken t SET t.used = true WHERE t.token = :token")
    void updateUsedByToken(final String token);

    //check if user has already requested a password reset
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM PasswordResetToken t WHERE t.user.id = :userId AND t.used = false")
    boolean existsByUserIdAndUsedIsFalse(final Long userId);

    @Transactional
    @Query("SELECT t FROM PasswordResetToken t WHERE t.used = false AND t.isExpired = false")
    Stream<PasswordResetToken> findAllNotExpired();

    @Modifying
    @Transactional
    @Query("UPDATE PasswordResetToken t SET t.isExpired = true WHERE t.token = :token")
    void updateExpiredByToken(final String token);
}
