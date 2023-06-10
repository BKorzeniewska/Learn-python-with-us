package com.example.learnpython.comment;


import com.example.learnpython.comment.model.Comment;
import com.example.learnpython.comment.model.CommentResponse;
import com.example.learnpython.comment.model.CreateCommentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toComment(final CreateCommentRequest createCommentRequest);

    @Mapping(target="userDetails.id", source="user.id")
    @Mapping(target="userDetails.nickname", source="user.nickname")
    @Mapping(target="userDetails.email", source="user.email")
    @Mapping(target="userDetails.firstname", source="user.firstname")

    CommentResponse toCommentResponse(final Comment comment);
}
