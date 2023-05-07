package com.example.learnpython.user;

import com.example.learnpython.user.model.UserInfoResponse;

public interface UserService {
    UserInfoResponse getUserInfo(final long userId);
    void updateUserLevelAndExpById(final long id, final long gainedExp);
    void deleteUserByEmail(final String email);
}
