package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.StudentDTO;
import com.swerve.backend.subject.dto.SubjectDTO;
import com.swerve.backend.subject.dto.SubjectEnrollmentDTO;
import com.swerve.backend.subject.dto.TeacherDTO;
import com.swerve.backend.subject.model.Course;
import com.swerve.backend.subject.model.StudentsPerGroup_OfferedCourse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubjectEnrollmentMapper
        extends BaseMapper<StudentsPerGroup_OfferedCourse, SubjectEnrollmentDTO, Long> {
    @Mapping(source = "studentId", target = "student")
    SubjectEnrollmentDTO toDTO(StudentsPerGroup_OfferedCourse studentsPerGroupOfferedCourse);

    @Mapping(source = "student.id", target = "studentId")
    StudentsPerGroup_OfferedCourse toModel(SubjectEnrollmentDTO subjectEnrollmentDTO);

    StudentDTO studentDTOFromId(Long id);

    @Mapping(target = "studyProgram", ignore = true)
    @Mapping(source = "professorId", target = "professor")
    @Mapping(source = "assistantId", target = "assistant")
    SubjectDTO toDTO(Course course);

    TeacherDTO teacherDTOFromId(Long id);
}
