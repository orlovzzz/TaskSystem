package org.diploma.projectservice.app.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.diploma.projectservice.adapter.repository.ProjectRepository;
import org.diploma.projectservice.app.api.exception.ProjectAlreadyExistsException;
import org.diploma.projectservice.app.api.exception.ProjectNotFoundException;
import org.diploma.projectservice.app.api.project.ProjectService;
import org.diploma.projectservice.app.api.security.SecurityService;
import org.diploma.projectservice.entity.Project;
import org.diploma.projectservice.entity.ProjectUser;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static org.diploma.projectservice.entity.enums.Permission.ADMIN;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final SecurityService securityService;

    @Override
    public void createProject(Project project) {
        var existingProject = projectRepository.findProjectByName(project.getName());
        if (existingProject.isPresent()) {
            throw new ProjectAlreadyExistsException("Project with name " + project.getName() + " already exists");
        }
        var user = securityService.getAuthorizedUser();
        var owner = ProjectUser.builder()
                .project(project)
                .login(user.getLogin())
                .permission(ADMIN)
                .role("Owner")
                .build();
        project.setOwner(user.getLogin());
        if (CollectionUtils.isEmpty(project.getUsers())) {
            project.setUsers(new ArrayList<>());
        }
        project.getUsers().add(owner);
        projectRepository.save(project);
    }

    @Override
    public Project getProject(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));
    }

    @Override
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    @Override
    public void updateProject(Long id, Project project) {
        var existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));
        project.getUsers().forEach(user -> user.setProject(existingProject));
        changeProjectData(existingProject, project);
        projectRepository.save(existingProject);
    }

    @Override
    public void deleteProject(Long id) {
        var existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));
        projectRepository.delete(existingProject);
    }

    @Override
    public void addUsersToProject(List<ProjectUser> users, Long id) {
        var project = getProject(id);
        users.forEach(user -> user.setProject(project));
        project.getUsers().addAll(users);
        projectRepository.save(project);
    }

    @Override
    public void deleteUsersFromProject(List<ProjectUser> users, Long id) {
        var project = getProject(id);
        var projectUsers = project.getUsers();
        users.forEach(u -> projectUsers.removeIf(user -> user.getLogin().equals(u.getLogin())));
        projectRepository.save(project);
    }

    @Override
    public void addTaskToProject(Long taskId, Long id) {
        var project = getProject(id);
        project.getTasks().add(taskId);
        projectRepository.save(project);
    }

    @Override
    public void deleteTaskFromProject(Long taskId, Long id) {
        var project = getProject(id);
        project.getTasks().remove(taskId);
        projectRepository.save(project);
    }

    private void changeProjectData(Project oldData, Project newData) {
        oldData.setName(newData.getName());
        oldData.setDescription(newData.getDescription());

        oldData.getUsers().removeIf(u -> !newData.getUsers().contains(u) && !u.getLogin().equals(oldData.getOwner()));
        newData.getUsers().forEach(u -> {
            if (!oldData.getUsers().contains(u)) {
                oldData.getUsers().add(u);
            }
        });

        oldData.setTasks(newData.getTasks());
    }
}
