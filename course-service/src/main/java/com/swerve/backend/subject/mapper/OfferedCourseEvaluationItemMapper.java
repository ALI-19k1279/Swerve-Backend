package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.OfferedCourseEvaluationItemDTO;
import com.swerve.backend.subject.model.OfferedCourseEvaluationItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OfferedCourseEvaluationItemMapper extends BaseMapper<OfferedCourseEvaluationItem, OfferedCourseEvaluationItemDTO,Long> {

    OfferedCourseEvaluationItemDTO toDTO(OfferedCourseEvaluationItem offeredCourseEvaluationItem);

    OfferedCourseEvaluationItem toModel(OfferedCourseEvaluationItemDTO offeredCourseEvaluationItemDTO);
}
