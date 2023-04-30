package com.swerve.backend.subject.dto;

import com.swerve.backend.shared.dto.BaseDTO;
import com.swerve.backend.subject.model.Group;
import com.swerve.backend.subject.model.OfferedCourse;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapping;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AttendanceDTO extends BaseDTO<Long> {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String date;


    private String duration;


    private String status;


    private String studentId;

    private Long groupId;

    private Long offeredCourseId;

}
