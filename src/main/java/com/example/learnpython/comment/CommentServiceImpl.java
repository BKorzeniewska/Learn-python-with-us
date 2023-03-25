package com.example.learnpython.comment;

import com.example.learnpython.comment.exception.CommentIllegalStateException;
import com.example.learnpython.comment.exception.CommentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    @Override
    public CommentResponse createComment(CreateCommentRequest commentRequest) {

        //validate articleId
        //validate userId

        if(commentRequest.getContent().isBlank()){
            throw new CommentIllegalStateException("Comment content cannot be blank");
        }

        var comment = commentMapper.toComment(commentRequest);
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.save(comment);
        return commentMapper.toCommentResponse(comment);
    }

    @Override
    public List<CommentResponse> getCommentsByArticleId(Long articleId) {
        var comments = commentRepository
                .findByArticleId(articleId)
                .orElseThrow(() -> new CommentNotFoundException("Comments not found"));
        return comments.stream().map(commentMapper::toCommentResponse).toList();
    }
}
