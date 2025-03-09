package org.diploma.taskservice.adapter.rest;

import org.diploma.taskservice.app.api.exception.TaskException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TaskControllerExceptionHandler {
    @ExceptionHandler(exception = TaskException.class)
    public ResponseEntity<String> handleTaskException(TaskException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }
}
