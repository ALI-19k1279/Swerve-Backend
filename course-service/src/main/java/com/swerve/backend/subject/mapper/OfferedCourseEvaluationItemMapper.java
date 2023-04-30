package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.OfferedCourseEvaluationItemDTO;
import com.swerve.backend.subject.model.OfferedCourseEvaluationItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OfferedCourseEvaluationItemMapper extends BaseMapper<OfferedCourseEvaluationItem, OfferedCourseEvaluationItemDTO,Long> {

    @Override
    @Named("default")
    OfferedCourseEvaluationItemDTO toDTO(OfferedCourseEvaluationItem offeredCourseEvaluationItem);

    @Override
    @Named("default")
    OfferedCourseEvaluationItem toModel(OfferedCourseEvaluationItemDTO offeredCourseEvaluationItemDTO);
}
