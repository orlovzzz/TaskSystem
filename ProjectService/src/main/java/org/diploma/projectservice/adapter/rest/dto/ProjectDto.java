package org.diploma.projectservice.adapter.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private String owner;
    private List<Long> tasks;
    private List<ProjectUserDto> users;
}
