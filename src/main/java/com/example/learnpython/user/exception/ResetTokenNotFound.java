package com.example.learnpython.user.exception;

import com.example.learnpython.exception.BaseServiceException;
import org.springframework.http.HttpStatus;

public class ResetTokenNotFound extends BaseServiceException {

    public ResetTokenNotFound(String message, String errorCode) {
        super(message, errorCode, HttpStatus.NOT_FOUND);
    }
}
