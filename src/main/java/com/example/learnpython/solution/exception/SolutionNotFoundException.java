package com.example.learnpython.solution.exception;

import com.example.learnpython.exception.BaseServiceException;
import org.springframework.http.HttpStatus;

public class SolutionNotFoundException extends BaseServiceException {
    public SolutionNotFoundException(String message, String errorCode) {
        super(message, errorCode, HttpStatus.NOT_FOUND);
    }
}
