package org.diploma.projectservice.app.api.user;

import org.diploma.projectservice.adapter.rest.dto.ProjectUserDto;

import java.util.List;

public interface UserService {
    List<ProjectUserDto> getUsers(List<ProjectUserDto> users);
}
