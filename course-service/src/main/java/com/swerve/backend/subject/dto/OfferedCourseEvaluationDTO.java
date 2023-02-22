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
public class OfferedCourseEvaluationDTO extends BaseDTO<Long> {

    @NotBlank(message = "Code is mandatory")
    private String code;

    @NotBlank(message = "StudentID is mandatory")
    private String studentID;

    private int marksObtained;

    private int noOfAttempts;
}
