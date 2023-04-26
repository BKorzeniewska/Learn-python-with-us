package com.example.learnpython.solution.exception;

import com.example.learnpython.exception.BaseServiceException;
import org.springframework.http.HttpStatus;

public class AddSolutionException extends BaseServiceException {
    public AddSolutionException(String message, String errorCode) {
        super(message, errorCode, HttpStatus.BAD_REQUEST);
    }
}
