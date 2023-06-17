package com.example.learnpython.comment.service;

import com.example.learnpython.comment.exception.CommentNotFoundException;
import com.example.learnpython.comment.model.Comment;
import com.example.learnpython.comment.repository.CommentRepository;
import com.example.learnpython.user.exception.UserNotFoundException;
import com.example.learnpython.user.exception.UserRequestException;
import com.example.learnpython.user.model.entity.User;
import com.example.learnpython.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentAdminServiceImpl implements CommentAdminService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    @Override
    public void deleteComment(final Long commentId, final String token) {

        if (token == null || !token.startsWith("Bearer ")) {
            throw new UserRequestException("Token or userId must be provided", "USER_NOT_FOUND");
        }
        final String jwt = token.substring(7);
        final User user = userRepository.findByToken(jwt).orElseThrow(() ->
                new UserNotFoundException("User with provided token not found", "USER_NOT_FOUND"));

        if (commentId == null) {
            throw new CommentNotFoundException("Comment ID cannot be null", "COMMENT_ID_NULL");
        }

        if (user.getRole().getValue() < 3) {
            checkIfCommentIsCreatedByUser(commentId, user);
        }

        commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment with provided ID not found", "COMMENT_NOT_FOUND"));
        commentRepository.deleteById(commentId);
    }

    private void checkIfCommentIsCreatedByUser(final Long commentId, final User user) {
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment with provided ID not found", "COMMENT_NOT_FOUND"));
        if (!comment.getUser().equals(user)) {
            throw new UserRequestException("User does not have permission to delete comment", "USER_NOT_AUTHORIZED");
        }
    }
}
