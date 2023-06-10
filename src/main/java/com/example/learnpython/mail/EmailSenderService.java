package com.example.learnpython.mail;

import com.example.learnpython.auth.RegisterRequest;
import com.example.learnpython.user.model.dto.ResetPasswordRequest;

public interface EmailSenderService {

    void sendRegisterEmail(final RegisterRequest request);

    void sendResetPasswordEmail(final ResetPasswordRequest request);

}
