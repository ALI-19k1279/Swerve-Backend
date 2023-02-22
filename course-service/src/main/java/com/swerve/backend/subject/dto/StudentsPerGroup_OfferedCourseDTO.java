package com.swerve.backend.subject.dto;

import com.swerve.backend.shared.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class StudentsPerGroup_OfferedCourseDTO extends BaseDTO<Long> {

    @NotBlank(message = "StudentID is mandatory")
    private Long studentID;

    @NotBlank(message = "isEnrolled is mandatory")
    private boolean isEnrolled;

}
