package com.swerve.backend.subject.dto;

import com.swerve.backend.shared.dto.BaseDTO;
import com.swerve.backend.subject.model.Cycle;
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
public class OfferedCourseDTO extends BaseDTO<Long> {

    @NotBlank(message = "Course is mandatory")
    private CourseDTO courseID;
    @NotBlank(message = "Fee is mandatory")
    private int fee;

    private String postsAndAnnouncements;
    @NotBlank(message = "Instructor is mandatory")
    private InstructorDTO instructor;

    @NotBlank(message = "Cycle is mandatory")
    private CycleDTO cycle;
}
