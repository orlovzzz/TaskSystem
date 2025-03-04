package org.diploma.userservice.app.api.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends UserException {
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public UserNotFoundException(String message) {
        super(message);
    }
}
