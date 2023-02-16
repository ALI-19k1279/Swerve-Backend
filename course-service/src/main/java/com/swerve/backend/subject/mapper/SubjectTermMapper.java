package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.SubjectDTO;
import com.swerve.backend.subject.dto.SubjectTermDTO;
import com.swerve.backend.subject.dto.TeacherDTO;
import com.swerve.backend.subject.model.Subject;
import com.swerve.backend.subject.model.SubjectTerm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubjectTermMapper extends BaseMapper<SubjectTerm, SubjectTermDTO, Long> {
    @Mapping(source = "teacherId", target = "teacher")
    SubjectTermDTO toDTO(SubjectTerm SubjectTerm);

    @Mapping(source = "teacher.id", target = "teacherId")
    SubjectTerm toModel(SubjectTermDTO SubjectTermDTO);

    TeacherDTO teacherDTOFromId(Long id);

    @Mapping(target = "studyProgram", ignore = true)
    @Mapping(target = "professor", ignore = true)
    @Mapping(target = "assistant", ignore = true)
    SubjectDTO toDTO(Subject subject);
}
