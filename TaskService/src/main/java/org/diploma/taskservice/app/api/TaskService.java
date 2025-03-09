package org.diploma.taskservice.app.api;

import org.diploma.taskservice.entity.Task;

import java.util.List;

public interface TaskService {
    List<Task> getAllTasks(Long projectId);
    Task getTask(Long id);
    void createTask(Task task);
    void updateTask(Long id, Task task);
    void deleteTask(Long id);
}
