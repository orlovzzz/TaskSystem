package org.diploma.projectservice.adapter.rest.mapper;

import org.diploma.projectservice.adapter.rest.dto.ProjectUserDto;
import org.diploma.projectservice.entity.ProjectUser;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ProjectUserMapper {
//    default ProjectUser toEntity(ProjectUserDto dto) {
//        return ProjectUser.builder()
//                .id(ProjectUser.ProjectUserId.builder().login(dto.getLogin()).build())
//                .role(dto.getRole())
//                .permission(dto.getPermission())
//                .build();
//    }
//    default ProjectUserDto toDto(ProjectUser entity) {
//        return ProjectUserDto.builder()
//                .login(entity.getId().getLogin())
//                .permission(entity.getPermission())
//                .role(entity.getRole())
//                .build();
//    }
    ProjectUser toEntity(ProjectUserDto dto);
    ProjectUserDto toDto(ProjectUser entity);
    List<ProjectUserDto> toDtoList(List<ProjectUser> entities);
    List<ProjectUser> toEntityList(List<ProjectUserDto> dtos);
}
