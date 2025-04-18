package org.diploma.userservice.app.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.diploma.userservice.app.api.security.SecurityService;
import org.springframework.stereotype.Service;
import org.diploma.userservice.adapter.repository.UserRepository;
import org.diploma.userservice.app.api.security.IdentityProvider;
import org.diploma.userservice.app.api.user.UserService;
import org.diploma.userservice.app.api.exception.UserAlreadyExistsException;
import org.diploma.userservice.app.api.exception.UserNotFoundException;
import org.diploma.userservice.entity.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final IdentityProvider identityProvider;
    private final SecurityService securityService;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String login) {
        log.info("Getting user by login: {}", login);
        return getUserOrThrow(login);
    }

    @Override
    public void createUser(User newUser) {
        log.info("Creating new user: {}", newUser);
        var user = userRepository.findById(newUser.getLogin());
        if (user.isPresent()) {
            throw new UserAlreadyExistsException("User with login " + newUser.getLogin() + " already exists");
        }
        identityProvider.createUser(newUser);
        userRepository.save(newUser);
    }

    @Override
    public void updateUser(User newUser) {
        var login = securityService.getAuthorizedUser().getLogin();
        log.info("Updating login {}, user: {}", login, newUser);
        getUserOrThrow(login);
        identityProvider.updateUser(login, newUser);
        newUser.setLogin(login);
        userRepository.save(newUser);
    }

    @Override
    public void deleteUser() {
        var login = securityService.getAuthorizedUser().getLogin();
        log.info("Deleting user {}", login);
        var user = getUserOrThrow(login);
        identityProvider.deleteUser(user);
        userRepository.delete(user);
    }

    private User getUserOrThrow(String login) {
        return userRepository.findUserByLoginIgnoreCase(login)
                .orElseThrow(() -> new UserNotFoundException("User with login " + login + " not found"));
    }
}
