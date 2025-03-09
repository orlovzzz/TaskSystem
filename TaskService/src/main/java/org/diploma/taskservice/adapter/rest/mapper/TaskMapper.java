package org.diploma.taskservice.adapter.rest.mapper;

import org.diploma.taskservice.adapter.rest.dto.CreateTaskDto;
import org.diploma.taskservice.adapter.rest.dto.TaskDto;
import org.diploma.taskservice.entity.Task;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TaskMapper {
    Task toEntity(CreateTaskDto dto);
    Task toEntity(TaskDto dto);
    TaskDto toDto(Task task);
    List<TaskDto> toDtoList(List<Task> tasks);
}
