package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.AttendanceDTO;
import com.swerve.backend.subject.dto.SessionDTO;
import com.swerve.backend.subject.model.Group;
import com.swerve.backend.subject.model.OfferedCourse;
import com.swerve.backend.subject.model.OfferedCourseAttendance;
import com.swerve.backend.subject.model.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring",  uses = GroupMapper.class)
public interface AttendanceMapper extends BaseMapper<OfferedCourseAttendance, AttendanceDTO,Long> {

    AttendanceMapper INSTANCE = Mappers.getMapper(AttendanceMapper.class);

    @Named("withIds")
    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "offeredCourse.id", target = "offeredCourseId")
    AttendanceDTO toDTOWithIds(OfferedCourseAttendance offeredCourseAttendance);

    @Named("withIds")
    @Mapping(source = "groupId", target = "group.id")
    @Mapping(source = "offeredCourseId", target = "offeredCourse.id")
    @Mapping(source = "date", target = "date", qualifiedByName = "toDate")
    OfferedCourseAttendance toModelWithIds(AttendanceDTO attendanceDTO);


    @Named("toDate")
    public static LocalDate toDate(String date)  {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    @Override
    @Named("default")
    AttendanceDTO toDTO(OfferedCourseAttendance offeredCourseAttendance);

    @Override
    @Named("default")
    OfferedCourseAttendance toModel(AttendanceDTO attendanceDTO);
}
