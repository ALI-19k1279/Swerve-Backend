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
public class OfferedCourseDTO extends BaseDTO<Long> {

    @NotBlank(message = "Fee is mandatory")
    private int fee;

    private String postsAndAnnouncements;
    @NotBlank(message = "InstructorID is mandatory")
    private int instructorID;
}
