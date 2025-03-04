package org.diploma.userservice.adapter.rest;

import lombok.RequiredArgsConstructor;
import org.diploma.userservice.adapter.rest.dto.CreateUserDto;
import org.diploma.userservice.adapter.rest.dto.UserDto;
import org.diploma.userservice.adapter.rest.mapper.UserMapper;
import org.diploma.userservice.app.api.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userMapper.toUserDtoList(userService.getAllUsers()));
    }

    @GetMapping("/{login}")
    public ResponseEntity<UserDto> getUserByLogin(@PathVariable String login) {
        var user = userMapper.toUserDto(userService.getUserById(login));
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto userDto) {
        userService.createUser(userMapper.toUserEntity(userDto));
        return ResponseEntity.status(CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody CreateUserDto userDto) {
        userService.updateUser(userMapper.toUserEntity(userDto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser();
        return ResponseEntity.noContent().build();
    }
}
