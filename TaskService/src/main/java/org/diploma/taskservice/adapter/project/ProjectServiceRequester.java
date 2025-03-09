package org.diploma.taskservice.adapter.project;

import org.diploma.taskservice.adapter.project.dto.ProjectDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userService", url = "${services.project-service-url}")
public interface ProjectServiceRequester {
    @GetMapping("/{id}")
    ProjectDto getProject(@PathVariable Long id);
}
