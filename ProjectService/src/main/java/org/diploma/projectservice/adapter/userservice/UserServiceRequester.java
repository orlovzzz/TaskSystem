package org.diploma.projectservice.adapter.userservice;

import org.diploma.projectservice.adapter.rest.dto.ProjectUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "userService", url = "${services.user-service-url}")
public interface UserServiceRequester {
    @GetMapping(value = "/{login}", consumes = APPLICATION_JSON_VALUE)
    ProjectUserDto getUser(@PathVariable String login);
}
