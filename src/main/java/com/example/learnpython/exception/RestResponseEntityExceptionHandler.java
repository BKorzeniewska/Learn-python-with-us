package com.example.learnpython.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Log4j2
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseServiceException.class)
    public ResponseEntity<ErrorResponse> handleProductServiceException(BaseServiceException exception) {

        log.error("BaseServiceException error: {}", exception.getMessage());
        log.error("BaseServiceException error code: {}", exception.getErrorCode());

        return new ResponseEntity<>(new ErrorResponse().builder()
                .errorMessage(exception.getMessage())
                .errorCode(exception.getErrorCode())
                .httpStatus(exception.getHttpStatus().name())
                .timestamp(LocalDateTime.now())
                .build(), exception.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception exception) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        log.error("Unexpected error: ", exception);
        log.error("Unexpected error message: {}", exception.getMessage());
        log.error("Unexpected error localized message: {}", exception.getLocalizedMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                "UNEXPECTED_ERROR",
                httpStatus.name(),
                exception.getMessage()
        );
        return new ResponseEntity<>(errorResponse, httpStatus);
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthenticationException exception) {
        log.error("AuthenticationException error: {}", exception.getMessage());

        return new ResponseEntity<>(new ErrorResponse().builder()
                .errorMessage(exception.getMessage())
                .errorCode("BAD_CREDENTIALS")
                .httpStatus(HttpStatus.BAD_REQUEST.name())
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleSQLException(DataIntegrityViolationException exception) {
        log.error("SQL error: {}", exception.getMessage());

        return new ResponseEntity<>(new ErrorResponse().builder()
                .errorMessage(exception.getMessage())
                .errorCode("BAD_REQUEST")
                .httpStatus(HttpStatus.BAD_REQUEST.name())
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException exception) {
        log.error("Access denied exception: {}", exception.getMessage());

        return new ResponseEntity<>(new ErrorResponse().builder()
                .errorMessage(exception.getMessage())
                .errorCode("ACCESS_DENIED")
                .httpStatus(HttpStatus.FORBIDDEN.name())
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.FORBIDDEN);
    }
}