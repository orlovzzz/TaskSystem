package org.diploma.taskservice.adapter.rest.dto;

import lombok.Data;
import org.diploma.taskservice.entity.enums.Priority;
import org.diploma.taskservice.entity.enums.Status;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class SubtaskDto {
    private Long id;
    private Long projectId;
    private String name;
    private String description;
    private String owner;
    private List<String> assignee = new ArrayList<>();
    private Instant createdAt;
    private Priority priority;
    private Status status;
    private Instant completedAt;
    private Instant startedAt;
}
