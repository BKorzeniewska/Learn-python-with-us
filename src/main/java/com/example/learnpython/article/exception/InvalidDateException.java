package com.example.learnpython.article.exception;

import com.example.learnpython.exception.BaseServiceException;
import org.springframework.http.HttpStatus;

public class InvalidDateException extends BaseServiceException{
    public InvalidDateException(String message, String errorCode) {
        super(message, errorCode, HttpStatus.NOT_ACCEPTABLE);
    }
}
