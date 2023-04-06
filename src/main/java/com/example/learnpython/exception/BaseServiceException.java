package com.example.learnpython.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseServiceException extends RuntimeException {

    private final String errorCode;
    private final HttpStatus httpStatus;

    public BaseServiceException(
            String message,
            String errorCode,
            HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}