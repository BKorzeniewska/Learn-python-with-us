package com.example.learnpython.comment;


import com.example.learnpython.comment.model.CommentResponse;
import com.example.learnpython.comment.model.CreateCommentRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toComment(CreateCommentRequest createCommentRequest);
    CommentResponse toCommentResponse(Comment comment);
}
