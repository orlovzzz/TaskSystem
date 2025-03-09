package org.diploma.taskservice.adapter.repository;

import org.diploma.taskservice.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findTasksByProjectId(Long projectId);
}
