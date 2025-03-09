package org.diploma.projectservice.app.api.exception;

import org.springframework.http.HttpStatus;

public abstract class ProjectException extends RuntimeException {

    protected ProjectException(String message) {
        super(message);
    }

    public abstract HttpStatus getStatus();
}
