package org.diploma.projectservice.app.api.exception;

import org.springframework.http.HttpStatus;

public class ProjectAlreadyExistsException extends ProjectException {
    private static final HttpStatus STATUS = HttpStatus.CONFLICT;

    public ProjectAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return STATUS;
    }
}
