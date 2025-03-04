package org.diploma.userservice.adapter.rest.mapper;

import org.diploma.userservice.adapter.rest.dto.CreateUserDto;
import org.diploma.userservice.adapter.rest.dto.UserDto;
import org.diploma.userservice.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {
    UserDto toUserDto(User user);
    User toUserEntity(UserDto userDto);
    List<UserDto> toUserDtoList(List<User> users);
    User toUserEntity(CreateUserDto createUserDto);
}
