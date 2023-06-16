package com.example.learnpython.comment.service;

import com.example.learnpython.comment.exception.CommentNotFoundException;
import com.example.learnpython.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentAdminServiceImpl implements CommentAdminService {
    private final CommentRepository commentRepository;

    @Override
    public void deleteComment(Long commentId) {
        if (commentId == null) {
            throw new CommentNotFoundException("Comment ID cannot be null", "COMMENT_ID_NULL");
        }
        commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment with provided ID not found", "COMMENT_NOT_FOUND"));
        commentRepository.deleteById(commentId);
    }
}
