package com.example.learnpython.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ExpiredTokenException {

    private String message;
    private String errorCode;
    private HttpStatus httpStatus;

}
