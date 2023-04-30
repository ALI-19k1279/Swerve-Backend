package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.CourseLearnerProgressDTO;
import com.swerve.backend.subject.dto.LearnerDTO;
import com.swerve.backend.subject.model.CourseLearnerProgress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CourseLearnerProgressMapper extends BaseMapper<CourseLearnerProgress, CourseLearnerProgressDTO,Long> {

    @Override
    @Named("default")
    @Mapping(source = "studentID", target = "learner")

    CourseLearnerProgressDTO toDTO(CourseLearnerProgress courseLearnerProgress);

    @Override
    @Named("default")
    @Mapping(source = "learner.id", target = "studentID")
    CourseLearnerProgress toModel(CourseLearnerProgressDTO courseLearnerProgressDTO);

    LearnerDTO learnerDTOFromId(Long id);
}
