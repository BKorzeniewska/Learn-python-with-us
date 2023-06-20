package com.example.learnpython.comment.repository;

import com.example.learnpython.comment.model.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<Comment>> findByArticleId(Long articleId);

    Optional<List<Comment>> findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Comment c WHERE c.id = :commentId")
    void deleteById(Long commentId);

    @Transactional
    @Modifying
    @Query("UPDATE Comment c SET c.article = null WHERE c.id = :articleId")
    int updateArticleToNull(final Long articleId);
}
