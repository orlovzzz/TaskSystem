package org.diploma.projectservice.adapter.rest.mapper;

import org.diploma.projectservice.adapter.rest.dto.CreateProjectDto;
import org.diploma.projectservice.adapter.rest.dto.ProjectDto;
import org.diploma.projectservice.entity.Project;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = ProjectUserMapper.class)
public interface ProjectMapper {
    Project toEntity(CreateProjectDto dto);
    Project toEntity(ProjectDto dto);
    ProjectDto toDto(Project project);
    List<ProjectDto> toDtoList(List<Project> entity);
}
