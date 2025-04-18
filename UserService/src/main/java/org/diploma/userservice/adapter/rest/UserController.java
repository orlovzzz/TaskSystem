package org.diploma.userservice.adapter.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Controller", description = "Управление пользователями")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Получить список всех пользователей")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        var users = userService.getAllUsers();
        log.info("Get all users: {}", users);
        return ResponseEntity.ok(userMapper.toUserDtoList(users));
    }

    @Operation(summary = "Получить пользователя по логину")
    @GetMapping(value = "/{login}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserByLogin(@PathVariable String login) {
        var user = userMapper.toUserDto(userService.getUserById(login));
        return ResponseEntity.ok().body(user);
    }

    @Operation(summary = "Создать нового пользователя")
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto userDto) {
        userService.createUser(userMapper.toUserEntity(userDto));
        return ResponseEntity.status(CREATED).build();
    }

    @Operation(summary = "Обновить пользователя")
    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody CreateUserDto userDto) {
        userService.updateUser(userMapper.toUserEntity(userDto));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser();
        return ResponseEntity.noContent().build();
    }
}

