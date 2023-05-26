package com.example.learnpython.user.model;

public record GetUsersRequest(
        Integer pageNumber,
        String query
) {
}
