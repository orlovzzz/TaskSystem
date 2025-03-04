package org.diploma.projectservice.adapter.rest;

import org.diploma.projectservice.app.api.exception.ProjectException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectControllerExceptionHandler {
    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<String> handleProjectAlreadyExistsException(ProjectException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }
}
