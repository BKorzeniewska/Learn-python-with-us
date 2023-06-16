package com.example.learnpython.user.service;

import com.example.learnpython.user.model.dto.ModifyUserRequest;
import com.example.learnpython.user.model.dto.UserInfoResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<UserInfoResponse> getUserInfo(final String token, final Long userId);

    ResponseEntity<UserInfoResponse> modifyUser(final String token, final ModifyUserRequest modifyUserRequest);

    void updateUserLevelAndExpById(final long id, final int gainedExp);

    void deleteUserByEmail(final String email);
}
