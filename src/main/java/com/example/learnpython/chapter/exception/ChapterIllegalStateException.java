package com.example.learnpython.chapter.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ChapterIllegalStateException extends RuntimeException {
    public ChapterIllegalStateException(String message) {
        super(message);
    }
}
