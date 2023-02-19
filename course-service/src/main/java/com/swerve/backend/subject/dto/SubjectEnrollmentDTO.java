package com.swerve.backend.subject.dto;

import com.swerve.backend.shared.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;
//import javax.validation.constraints.Max;
//import javax.validation.constraints.Min;
//import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubjectEnrollmentDTO extends BaseDTO<Long> {
    @NotNull(message = "Student is mandatory")
    private StudentDTO student;

    @NotNull(message = "Course is mandatory")
    private SubjectDTO subject;

    @Min(value = 0, message = "Extra points must be greater than or equal to 0")
    @Max(value = 10, message = "Extra points must be less than or equal to 10")
    private Integer extraPoints;

    @Min(value = 6, message = "Grade must be greater than or equal to 6")
    @Max(value = 10, message = "Grade must be less than or equal to 10")
    private Integer grade;
}
