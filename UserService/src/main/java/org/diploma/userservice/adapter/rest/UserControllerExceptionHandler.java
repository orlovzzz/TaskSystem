package org.diploma.userservice.adapter.rest;

import org.diploma.userservice.app.api.exception.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserControllerExceptionHandler {
    @ExceptionHandler(exception = UserException.class)
    public ResponseEntity<String> handleUserException(UserException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }
}
