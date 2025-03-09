package org.diploma.taskservice.adapter.user;

import org.diploma.taskservice.adapter.user.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userService", url = "${services.user-service-url}")
public interface UserServiceRequester {
    @GetMapping("/{login}")
    UserDto getUser(@PathVariable String login);
}
