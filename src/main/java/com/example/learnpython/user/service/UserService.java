package com.example.learnpython.user.service;

import com.example.learnpython.user.model.dto.UserInfoResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<UserInfoResponse> getUserInfo(final String token, final Long userId);
    void updateUserLevelAndExpById(final long id, final long gainedExp);
    void deleteUserByEmail(final String email);
}
