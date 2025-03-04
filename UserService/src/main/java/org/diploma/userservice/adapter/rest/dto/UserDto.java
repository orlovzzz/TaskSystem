package org.diploma.userservice.adapter.rest.dto;

import lombok.Data;

@Data
public class UserDto {
    private String login;
    private String firstName;
    private String lastName;
    private String email;
}
