package org.diploma.projectservice.adapter.rest.dto;

import lombok.Builder;
import lombok.Data;
import org.diploma.projectservice.entity.enums.Permission;

@Data
@Builder
public class ProjectUserDto {
    private String login;
    private String firstName;
    private String lastName;
    private String role;
    private Permission permission;
}
