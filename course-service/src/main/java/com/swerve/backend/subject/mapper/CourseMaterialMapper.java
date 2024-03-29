package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.CourseMaterialDTO;
import com.swerve.backend.subject.dto.InstructorDTO;
import com.swerve.backend.subject.model.CourseMaterial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CourseMaterialMapper extends BaseMapper<CourseMaterial, CourseMaterialDTO,Long> {

    @Override
    @Named("default")
    @Mapping(source = "teacherId", target = "teacher")
    CourseMaterialDTO toDTO(CourseMaterial courseMaterial);

    @Override
    @Named("default")
    @Mapping(source = "teacher.id", target = "teacherId")
    CourseMaterial toModel(CourseMaterialDTO courseMaterialDTO);

    InstructorDTO instructorDTOFromId(Long id);
}
