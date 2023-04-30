package com.swerve.backend.userservice.mapper;

import com.swerve.backend.userservice.dto.TeacherDTO;
import com.swerve.backend.userservice.model.Teacher;
import com.swerve.backend.shared.dto.UserDTO;
import com.swerve.backend.shared.mapper.BaseMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TeacherMapper extends BaseMapper<Teacher, TeacherDTO, Long> {
    @Override
    @Named("default")
    @Mapping(source = "userId", target = "user")
    TeacherDTO toDTO(Teacher teacher);

    @Override
    @Named("default")
    @Mapping(source = "user.id", target = "userId")
    Teacher toModel(TeacherDTO teacherDTO);

    UserDTO userDTOFromId(Long id);
}
