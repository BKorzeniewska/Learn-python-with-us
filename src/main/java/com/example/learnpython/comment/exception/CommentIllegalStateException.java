package com.example.learnpython.comment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CommentIllegalStateException extends RuntimeException {
    public CommentIllegalStateException(String message) {
        super(message);
    }
}
