package org.diploma.userservice.app.api.exception;

import org.springframework.http.HttpStatus;

public class UserException extends RuntimeException {
    private static final HttpStatus STATUS = HttpStatus.OK;

    public UserException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return STATUS;
    }
}
