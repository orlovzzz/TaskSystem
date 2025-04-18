package org.diploma.projectservice.adapter.rest;

import lombok.RequiredArgsConstructor;
import org.diploma.projectservice.adapter.rest.dto.CreateProjectDto;
import org.diploma.projectservice.adapter.rest.dto.ProjectDto;
import org.diploma.projectservice.adapter.rest.dto.ProjectUserDto;
import org.diploma.projectservice.adapter.rest.mapper.ProjectMapper;
import org.diploma.projectservice.adapter.rest.mapper.ProjectUserMapper;
import org.diploma.projectservice.app.api.project.ProjectService;
import org.diploma.projectservice.app.api.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;


@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
@Tag(name = "Project Controller", description = "Управление проектами")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final ProjectUserMapper projectUserMapper;

    @Operation(summary = "Создать новый проект")
    @PostMapping
    public ResponseEntity<Void> createProject(@RequestBody CreateProjectDto dto) {
        projectService.createProject(projectMapper.toEntity(dto));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Получить список всех проектов")
    @GetMapping
    public ResponseEntity<List<ProjectDto>> getProjects() {
        var projects = projectMapper.toDtoList(projectService.getProjects());
        projects.forEach(project -> project.setUsers(userService.getUsers(project.getUsers())));
        return ResponseEntity.ok().body(projects);
    }

    @Operation(summary = "Получить проект по ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable Long id) {
        var project = projectMapper.toDto(projectService.getProject(id));
        project.setUsers(userService.getUsers(project.getUsers()));
        return ResponseEntity.ok().body(project);
    }

    @Operation(summary = "Обновить проект по ID")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProject(@PathVariable Long id, @RequestBody ProjectDto dto) {
        projectService.updateProject(id, projectMapper.toEntity(dto));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Удалить проект по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Добавить пользователей в проект")
    @PostMapping("/users/{id}")
    public ResponseEntity<Void> addUsersToProject(@RequestBody List<ProjectUserDto> users, @PathVariable Long id) {
        projectService.addUsersToProject(projectUserMapper.toEntityList(users), id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Удалить пользователей из проекта")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUsersFromProject(@RequestBody List<ProjectUserDto> users, @PathVariable Long id) {
        projectService.deleteUsersFromProject(projectUserMapper.toEntityList(users), id);
        return ResponseEntity.noContent().build();
    }
}
