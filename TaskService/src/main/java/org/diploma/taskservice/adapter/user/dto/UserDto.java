package org.diploma.taskservice.adapter.user.dto;

import lombok.Data;

@Data
public class UserDto {
    private final String login;
    private final String firstName;
    private final String lastName;
    private final String email;
}
