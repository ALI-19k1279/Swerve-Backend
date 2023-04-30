package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.LearnerDTO;
import com.swerve.backend.subject.dto.OfferedCourseEvaluationDTO;
import com.swerve.backend.subject.model.OfferedCourseEvaluation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OfferedCourseEvaluationMapper extends BaseMapper<OfferedCourseEvaluation, OfferedCourseEvaluationDTO,Long> {

    @Override
    @Named("default")
    @Mapping(source = "studentID", target = "student")
    OfferedCourseEvaluationDTO toDTO(OfferedCourseEvaluation offeredCourseEvaluation);

    @Override
    @Named("default")
    @Mapping(source = "student.id", target = "studentID")
    OfferedCourseEvaluation toModel(OfferedCourseEvaluationDTO offeredCourseEvaluationDTO);

    LearnerDTO learnerDTOFromId(Long id);
}
