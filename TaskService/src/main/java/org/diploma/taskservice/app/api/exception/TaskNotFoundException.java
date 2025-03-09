package org.diploma.taskservice.app.api.exception;

import org.springframework.http.HttpStatus;

public class TaskNotFoundException extends TaskException {
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public TaskNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return STATUS;
    }
}
