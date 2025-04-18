package org.diploma.taskservice.adapter.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.diploma.taskservice.adapter.user.dto.UserDto;
import org.diploma.taskservice.app.api.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceAdapter implements UserService {
    private final UserServiceRequester requester;

    @Override
    public List<String> getUserEmails(List<String> logins) {
        log.info("getUserEmails: {}", logins);
        return logins.stream()
            .map(requester::getUser)
            .map(UserDto::getEmail)
            .filter(Objects::nonNull)
            .toList();
    }
}
