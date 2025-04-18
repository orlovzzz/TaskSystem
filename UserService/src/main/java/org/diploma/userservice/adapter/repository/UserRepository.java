package org.diploma.userservice.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.diploma.userservice.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByLoginIgnoreCase(String login);
}
