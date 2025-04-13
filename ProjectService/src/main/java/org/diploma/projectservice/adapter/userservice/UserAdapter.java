package org.diploma.projectservice.adapter.userservice;

import feign.codec.DecodeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.diploma.projectservice.adapter.rest.dto.ProjectUserDto;
import org.diploma.projectservice.app.api.user.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserAdapter implements UserService {
    private final UserServiceRequester userServiceRequester;

    public List<ProjectUserDto> getUsers(List<ProjectUserDto> users) {
        if (users == null) {
            return List.of();
        }
        log.info("Getting full users data {}", users);
        return users.stream()
                .map(this::getUser)
                .toList();
    }

    private ProjectUserDto getUser(ProjectUserDto user) {
        try {
            var u = userServiceRequester.getUser(user.getLogin());
            user.setFirstName(u.getFirstName());
            user.setLastName(u.getLastName());
            return user;
        }
        catch (DecodeException e) {
            log.error(e.getMessage());
        }
        catch (Exception e) {
            log.error("An error occurred while getting user", e);
        }
        return null;
    }
}
