package org.diploma.projectservice.adapter.userservice;

import org.diploma.projectservice.adapter.rest.dto.ProjectUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userService", url = "${services.user-service-url}")
public interface UserServiceRequester {
    @GetMapping("/{login}")
    ProjectUserDto getUser(@PathVariable String login);
}
