package org.diploma.taskservice.adapter.rest;

import lombok.RequiredArgsConstructor;
import org.diploma.taskservice.adapter.rest.dto.CreateTaskDto;
import org.diploma.taskservice.adapter.rest.dto.TaskDto;
import org.diploma.taskservice.adapter.rest.mapper.TaskMapper;
import org.diploma.taskservice.app.api.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(@RequestParam("projectId") Long projectId) {
        return ResponseEntity.ok(taskMapper.toDtoList(taskService.getAllTasks(projectId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskMapper.toDto(taskService.getTask(id)));
    }

    @PostMapping
    public ResponseEntity<Void> createTask(@RequestBody CreateTaskDto dto) {
        taskService.createTask(taskMapper.toEntity(dto));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTask(@PathVariable Long id, @RequestBody TaskDto dto) {
        taskService.updateTask(id, taskMapper.toEntity(dto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
