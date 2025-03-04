package org.diploma.userservice.app.api.security;

import org.diploma.userservice.entity.User;

public interface IdentityProvider {
    void createUser(User user);
    void updateUser(String login, User user);
    void deleteUser(User user);
}
