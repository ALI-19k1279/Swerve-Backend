package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.CourseModuleDTO;
import com.swerve.backend.subject.dto.OfferedCourseDTO;
import com.swerve.backend.subject.model.CourseModule;
import com.swerve.backend.subject.model.OfferedCourse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseModuleMapper extends BaseMapper<CourseModule, CourseModuleDTO,Long> {

    CourseModuleDTO toDTO(CourseModule courseModule);

    CourseModule toModel(CourseModuleDTO courseModuleDTO);

//    OfferedCourseDTO toDTO(OfferedCourse offeredCourse);
}
