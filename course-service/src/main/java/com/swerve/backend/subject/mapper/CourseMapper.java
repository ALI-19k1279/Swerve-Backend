package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.CourseDTO;
import com.swerve.backend.subject.dto.LearningTrackDTO;
import com.swerve.backend.subject.model.Course;
import com.swerve.backend.subject.model.LearningTrack;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CourseMapper extends BaseMapper<Course, CourseDTO,Long> {
    @Override
    @Named("default")
    CourseDTO toDTO(Course course);

    @Override
    @Named("default")
    Course toModel(CourseDTO CourseDTO);

    LearningTrackDTO toDTO(LearningTrack learningTrack);
}
