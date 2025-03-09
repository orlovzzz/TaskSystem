package org.diploma.taskservice.adapter.project;

import lombok.RequiredArgsConstructor;
import org.diploma.taskservice.app.api.ProjectService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectServiceAdapter implements ProjectService {
    private final ProjectServiceRequester requester;

    @Override
    public String getProjectName(Long id) {
        return requester.getProject(id).getName();
    }
}
