package org.diploma.projectservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.diploma.projectservice.entity.enums.Permission;

import java.util.Objects;

@Entity
@Table(name = "projects_users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Permission permission;
    private String role;
    private String login;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ProjectUser pu)) return false;
        return Objects.equals(login, pu.login) && Objects.equals(project.getId(), pu.project.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, project.getId());
    }
}
