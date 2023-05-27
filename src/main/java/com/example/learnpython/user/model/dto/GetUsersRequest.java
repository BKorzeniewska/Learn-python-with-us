package com.example.learnpython.user.model.dto;

public record GetUsersRequest(
        Integer pageNumber,
        String query
) {
}
