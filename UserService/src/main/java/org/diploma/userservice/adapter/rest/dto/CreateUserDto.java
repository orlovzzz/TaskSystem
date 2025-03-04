package org.diploma.userservice.adapter.rest.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateUserDto extends UserDto {
    private String password;
}
