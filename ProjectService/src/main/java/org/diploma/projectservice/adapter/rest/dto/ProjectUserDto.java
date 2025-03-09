package org.diploma.projectservice.adapter.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.diploma.projectservice.entity.enums.Permission;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUserDto {
    private String login;
    private String firstName;
    private String lastName;
    private String role;
    private Permission permission;
}
