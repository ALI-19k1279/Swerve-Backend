package com.swerve.backend.subject.mapper;


import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.AttendanceDTO;
import com.swerve.backend.subject.dto.CourseOutlineDTO;
import com.swerve.backend.subject.model.OfferedCourseAttendance;
import com.swerve.backend.subject.model.OfferedCourseOutline;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",  uses = GroupMapper.class)
public interface CourseOutlineMapper extends BaseMapper<OfferedCourseOutline, CourseOutlineDTO,Long> {
    CourseOutlineMapper INSTANCE = Mappers.getMapper(CourseOutlineMapper.class);
    @Override
    @Named("default")
    CourseOutlineDTO toDTO(OfferedCourseOutline offeredCourseOutline);

    @Override
    @Named("default")
    OfferedCourseOutline toModel(CourseOutlineDTO courseOutlineDTO);

    @IterableMapping(qualifiedByName = "default")
    List<CourseOutlineDTO> toDtoList(List<OfferedCourseOutline> offeredCourseOutlines);

    @IterableMapping(qualifiedByName = "default")
    List<OfferedCourseOutline> toModelList(List<CourseOutlineDTO> courseOutlineDTOs);


}
