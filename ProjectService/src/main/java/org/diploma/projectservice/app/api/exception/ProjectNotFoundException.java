package org.diploma.projectservice.app.api.exception;

import org.springframework.http.HttpStatus;

public class ProjectNotFoundException extends ProjectException {
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public ProjectNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return STATUS;
    }
}
