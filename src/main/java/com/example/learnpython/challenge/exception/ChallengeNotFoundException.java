package com.example.learnpython.challenge.exception;
import com.example.learnpython.exception.BaseServiceException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Getter
public class ChallengeNotFoundException extends BaseServiceException {

    public ChallengeNotFoundException(String message, String errorCode) {
        super(message, errorCode, HttpStatus.NOT_FOUND);
    }
}
