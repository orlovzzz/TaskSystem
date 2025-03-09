package org.diploma.projectservice.adapter.rest.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class CreateProjectDto {
    private String name;
    private String description;
    private List<ProjectUserDto> users;
}
