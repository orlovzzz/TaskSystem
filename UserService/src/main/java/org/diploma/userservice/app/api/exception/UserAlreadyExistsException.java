package org.diploma.userservice.app.api.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends UserException {
    private static final HttpStatus STATUS = HttpStatus.CONFLICT;

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
