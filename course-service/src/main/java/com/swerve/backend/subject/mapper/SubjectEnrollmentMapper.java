package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.StudentDTO;
import com.swerve.backend.subject.dto.SubjectDTO;
import com.swerve.backend.subject.dto.SubjectEnrollmentDTO;
import com.swerve.backend.subject.dto.TeacherDTO;
import com.swerve.backend.subject.model.Subject;
import com.swerve.backend.subject.model.SubjectEnrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubjectEnrollmentMapper
        extends BaseMapper<SubjectEnrollment, SubjectEnrollmentDTO, Long> {
    @Mapping(source = "studentId", target = "student")
    SubjectEnrollmentDTO toDTO(SubjectEnrollment subjectEnrollment);

    @Mapping(source = "student.id", target = "studentId")
    SubjectEnrollment toModel(SubjectEnrollmentDTO subjectEnrollmentDTO);

    StudentDTO studentDTOFromId(Long id);

    @Mapping(target = "studyProgram", ignore = true)
    @Mapping(source = "professorId", target = "professor")
    @Mapping(source = "assistantId", target = "assistant")
    SubjectDTO toDTO(Subject subject);

    TeacherDTO teacherDTOFromId(Long id);
}
