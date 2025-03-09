package org.diploma.taskservice.adapter.rest.dto;

import lombok.Data;
import org.diploma.taskservice.entity.Task;
import org.diploma.taskservice.entity.enums.Priority;

import java.util.List;

@Data
public class CreateTaskDto {
    private Long id;
    private Long projectId;
    private String name;
    private String description;
    private String owner;
    private List<String> assignee;
    private Priority priority;
    private Task majorTask;
}
