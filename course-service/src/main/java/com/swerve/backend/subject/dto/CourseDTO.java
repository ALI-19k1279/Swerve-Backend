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
public class CourseDTO extends BaseDTO<Long> {
    @NotBlank(message = "Code is mandatory")
    private String courseCode;

    @NotBlank(message = "Course title is mandatory")
    private String title;


    private String shortDescription;
    @NotBlank(message = "Credit is mandatory")
    private String credits;


}
