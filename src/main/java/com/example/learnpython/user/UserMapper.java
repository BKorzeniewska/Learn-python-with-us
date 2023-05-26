package com.example.learnpython.user;

import com.example.learnpython.user.model.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(final User user);
}
