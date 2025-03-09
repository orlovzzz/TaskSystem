package org.diploma.taskservice.app.impl;

import lombok.RequiredArgsConstructor;
import org.diploma.taskservice.adapter.repository.TaskRepository;
import org.diploma.taskservice.app.api.SecurityService;
import org.diploma.taskservice.app.api.TaskService;
import org.diploma.taskservice.app.api.exception.TaskNotFoundException;
import org.diploma.taskservice.entity.Task;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.diploma.taskservice.entity.enums.Status.CLOSED;
import static org.diploma.taskservice.entity.enums.Status.IN_PROGRESS;
import static org.diploma.taskservice.entity.enums.Status.OPEN;

@Component
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final SecurityService securityService;

    @Override
    public List<Task> getAllTasks(Long projectId) {
        return taskRepository.findTasksByProjectId(projectId);
    }

    @Override
    public Task getTask(Long id) {
        return taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
    }

    @Override
    public void createTask(Task task) {
        task.setStatus(OPEN);
        task.setOwner(securityService.getAuthorizedUser().getLogin());
        taskRepository.save(task);
    }

    @Override
    public void updateTask(Long id, Task task) {
        var oldTask = getTask(id);
        updateTaskData(oldTask, task);
        taskRepository.save(oldTask);
    }

    @Override
    public void deleteTask(Long id) {
        getTask(id);
        taskRepository.deleteById(id);
    }

    private void updateTaskData(Task oldTask, Task newTask) {
        oldTask.setName(newTask.getName());
        oldTask.setDescription(newTask.getDescription());
        oldTask.setPriority(newTask.getPriority());

        oldTask.getAssignee().removeIf(u -> !newTask.getAssignee().contains(u));
        newTask.getAssignee().forEach(u -> {
            if (!oldTask.getAssignee().contains(u)) {
                oldTask.getAssignee().add(u);
            }
        });

        oldTask.getSubtasks().removeIf(s -> !newTask.getSubtasks().contains(s));
        newTask.getSubtasks().forEach(s -> {
            if (!oldTask.getSubtasks().contains(s)) {
                oldTask.getSubtasks().add(s);
            }
        });

        oldTask.getComments().removeIf(c -> !newTask.getComments().contains(c));
        newTask.getComments().forEach(c -> {
            if (!oldTask.getComments().contains(c)) {
                oldTask.getComments().add(c);
            }
        });

        if (oldTask.getStatus() == OPEN && newTask.getStatus() == IN_PROGRESS) {
            oldTask.setStartedAt(Instant.ofEpochSecond(LocalTime.now().toEpochSecond(LocalDate.now(), ZoneOffset.UTC)));
        } else if (oldTask.getStatus() == IN_PROGRESS && newTask.getStatus() == CLOSED) {
            oldTask.setCompletedAt(Instant.ofEpochSecond(LocalTime.now().toEpochSecond(LocalDate.now(), ZoneOffset.UTC)));
        }
        oldTask.setStatus(newTask.getStatus());
    }
}
