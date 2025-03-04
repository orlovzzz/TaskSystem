package org.diploma.projectservice.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "projects")
@Getter
@Setter
@ToString
public class Project {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String owner;
    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "project_task", joinColumns = @JoinColumn(name = "project_id"))
    private List<Long> tasks;
    @OneToMany(mappedBy = "project", fetch = EAGER, cascade = ALL, orphanRemoval = true)
    private List<ProjectUser> users;
}
