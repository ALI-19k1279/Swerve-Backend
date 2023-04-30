package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.InstructorDTO;
import com.swerve.backend.subject.dto.OfferedCourseDTO;
import com.swerve.backend.subject.model.OfferedCourse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OfferedCourseMapper extends BaseMapper<OfferedCourse, OfferedCourseDTO, Long> {

    @Override
    @Named("default")
    @Mapping(source = "instructorId",target = "instructor")
    OfferedCourseDTO toDTO(OfferedCourse offeredCourse);

    @Override
    @Named("default")
    @Mapping(source = "instructor.id",target = "instructorId")
    OfferedCourse toModel(OfferedCourseDTO offeredCourseDTO);

    InstructorDTO instructorDTOFromId(Long id);

}
