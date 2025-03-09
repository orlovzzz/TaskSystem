package org.diploma.taskservice.app.api.exception;

import org.springframework.http.HttpStatus;

public abstract class TaskException extends RuntimeException {
    protected TaskException(String message) {
        super(message);
    }

    public abstract HttpStatus getStatus();
}
