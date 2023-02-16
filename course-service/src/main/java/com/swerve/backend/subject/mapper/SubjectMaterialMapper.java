package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.SubjectDTO;
import com.swerve.backend.subject.dto.SubjectMaterialDTO;
import com.swerve.backend.subject.dto.TeacherDTO;
import com.swerve.backend.subject.model.Subject;
import com.swerve.backend.subject.model.SubjectMaterial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubjectMaterialMapper
        extends BaseMapper<SubjectMaterial, SubjectMaterialDTO, Long> {
    @Mapping(source = "teacherId", target = "teacher")
    SubjectMaterialDTO toDTO(SubjectMaterial subjectMaterial);

    @Mapping(source = "teacher.id", target = "teacherId")
    SubjectMaterial toModel(SubjectMaterialDTO subjectMaterialDTO);

    TeacherDTO teacherDTOFromId(Long id);

    @Mapping(target = "studyProgram", ignore = true)
    @Mapping(target = "professor", ignore = true)
    @Mapping(target = "assistant", ignore = true)
    SubjectDTO toDTO(Subject subject);
}
