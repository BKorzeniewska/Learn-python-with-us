package com.example.learnpython.user;

import com.example.learnpython.exception.BaseServiceException;
import org.springframework.http.HttpStatus;

public class UserRequestException extends BaseServiceException {
    public UserRequestException(String message, String errorCode, HttpStatus httpStatus) {
        super(message, errorCode, httpStatus);
    }
}
