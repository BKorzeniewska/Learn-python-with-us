package com.example.learnpython.chapter.exception;


import com.example.learnpython.exception.BaseServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ChapterIllegalStateException extends BaseServiceException {
    public ChapterIllegalStateException(String message, String errorCode, HttpStatus httpStatus) {
        super(message, errorCode, httpStatus);
    }
}
