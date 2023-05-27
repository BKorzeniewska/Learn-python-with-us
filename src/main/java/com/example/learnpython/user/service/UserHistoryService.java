package com.example.learnpython.user.service;

public interface UserHistoryService {
    void saveHistory(final String bearerToken, final Long articleId);

    void deleteHistory(final String bearerToken, final Long articleId);
}
