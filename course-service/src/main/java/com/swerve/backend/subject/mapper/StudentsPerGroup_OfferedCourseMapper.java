package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.LearnerDTO;
import com.swerve.backend.subject.dto.StudentsPerGroup_OfferedCourseDTO;
import com.swerve.backend.subject.model.StudentsPerGroup_OfferedCourse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentsPerGroup_OfferedCourseMapper extends BaseMapper<StudentsPerGroup_OfferedCourse, StudentsPerGroup_OfferedCourseDTO,Long> {

    @Mapping(source = "studentId", target = "student")
    StudentsPerGroup_OfferedCourseDTO toDTO(StudentsPerGroup_OfferedCourse studentsPerGroupOfferedCourse);

    @Mapping(source = "student.id", target = "studentId")
    StudentsPerGroup_OfferedCourse toModel(StudentsPerGroup_OfferedCourseDTO studentsPerGroupOfferedCourseDTO);

    LearnerDTO learnerDTOFromId(Long id);
}
