package org.diploma.projectservice.app.api.project;

import org.diploma.projectservice.entity.Project;
import org.diploma.projectservice.entity.ProjectUser;

import java.util.List;

public interface ProjectService {
    void createProject(Project project);
    Project getProject(Long id);
    List<Project> getProjects();
    void updateProject(Long id, Project project);
    void deleteProject(Long id);
    void addUsersToProject(List<ProjectUser> users, Long id);
    void deleteUsersFromProject(List<ProjectUser> users, Long id);
}
