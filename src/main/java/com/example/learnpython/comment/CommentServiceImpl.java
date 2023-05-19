package com.example.learnpython.comment;

import com.example.learnpython.article.Article;
import com.example.learnpython.article.ArticleRepository;
import com.example.learnpython.comment.exception.CommentIllegalStateException;
import com.example.learnpython.comment.exception.CommentNotFoundException;
import com.example.learnpython.comment.model.CommentResponse;
import com.example.learnpython.comment.model.CreateCommentRequest;
import com.example.learnpython.user.User;
import com.example.learnpython.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    @Override
    public CommentResponse createComment(final CreateCommentRequest commentRequest, final String bearerToken) {

        if (bearerToken == null || bearerToken.isBlank()) {
            throw new CommentIllegalStateException("Bearer token cannot be blank", "BEARER_TOKEN_BLANK");
        }

        //validate userId
        final String token = bearerToken.substring(7);
        final User user = userRepository.findByToken(token)
                .orElseThrow(() -> new CommentNotFoundException("User not found", "USER_NOT_FOUND"));

        //validate articleId
        final Article article = articleRepository.findById(commentRequest.getArticleId())
                .orElseThrow(() -> new CommentNotFoundException("Article not found", "ARTICLE_NOT_FOUND"));


        if (commentRequest.getContent().isBlank()) {
            throw new CommentIllegalStateException("Comment content cannot be blank", "COMMENT_CONTENT_BLANK");
        }

        var comment = commentMapper.toComment(commentRequest);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setArticle(article);
        comment.setUser(user);

        commentRepository.save(comment);
        return commentMapper.toCommentResponse(comment);
    }

    @Override
    public List<CommentResponse> getCommentsByArticleId(Long articleId) {
        var comments = commentRepository
                .findByArticleId(articleId)
                .orElseThrow(() -> new CommentNotFoundException("Comments not found", "COMMENTS_NOT_FOUND"));
        return comments.stream().map(commentMapper::toCommentResponse).toList();
    }
    @Override
    public List<CommentResponse> getCommentsByUserId(Long userId) {
        var comments = commentRepository
                .findByUserId(userId)
                .orElseThrow(() -> new CommentNotFoundException("Comments not found", "COMMENTS_NOT_FOUND"));
        return comments.stream().map(commentMapper::toCommentResponse).toList();

    }
}
