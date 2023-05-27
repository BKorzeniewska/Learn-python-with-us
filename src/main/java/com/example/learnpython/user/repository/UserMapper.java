package com.example.learnpython.user.repository;

import com.example.learnpython.user.model.dto.UserResponse;
import com.example.learnpython.user.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(final User user);
}
