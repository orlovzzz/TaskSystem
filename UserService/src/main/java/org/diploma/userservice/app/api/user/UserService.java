package org.diploma.userservice.app.api.user;

import org.diploma.userservice.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(String login);
    void createUser(User newUser);
    void updateUser(User newUser);
    void deleteUser();
}
