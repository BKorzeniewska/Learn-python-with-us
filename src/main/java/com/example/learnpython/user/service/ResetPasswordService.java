package com.example.learnpython.user.service;

import com.example.learnpython.user.model.dto.ResetPasswordRequest;

public interface ResetPasswordService {
    void resetPasswordUserRequest(final String userEmail);
    void resetPassword(final ResetPasswordRequest resetPasswordRequest);
}
