package com.swerve.backend.subject.dto;

import com.swerve.backend.shared.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class StudentsPerGroup_OfferedCourseDTO extends BaseDTO<Long> {

    @NotBlank(message = "Student is mandatory")
    private LearnerDTO student;

    @NotBlank(message = "Offered Course is mandatory")
    private OfferedCourseDTO offeredCourse;

    @NotBlank(message = "Group is mandatory")
    private GroupDTO group;

    @NotBlank(message = "isEnrolled is mandatory")
    private boolean isEnrolled;



}
