package org.diploma.taskservice.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.diploma.taskservice.entity.enums.Priority;
import org.diploma.taskservice.entity.enums.Status;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@ToString
public class Task {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "project_id")
    private Long projectId;
    private String name;
    private String description;
    private String owner;
    @ElementCollection
    @CollectionTable(name = "task_assignee", joinColumns = @JoinColumn(name = "task_id"))
    private List<String> assignee;
    @CreationTimestamp
    private Instant createdAt;
    private Priority priority;
    private Status status;
    @OneToMany(mappedBy = "majorTask", cascade = ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Task> subtasks;
    @ManyToOne
    @JoinColumn(name = "major_task_id")
    private Task majorTask;
    private Instant completedAt;
    private Instant startedAt;
    @OneToMany(mappedBy = "task", cascade = ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Task task)) return false;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
