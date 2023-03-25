package com.example.learnpython.comment;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toComment(CreateCommentRequest createCommentRequest);
    CommentResponse toCommentResponse(Comment comment);
}
