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
public class OfferedCourseEvaluationItemDTO extends BaseDTO<Long> {

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "canReattempt is mandatory")
    private boolean canReattempt;
    @NotBlank(message = "Total Marks are mandatory")
    private int totalMarks;

    @NotBlank(message = "Passing Marks are mandatory")
    private int passingMarks;
    @NotBlank(message = "Offered Course Evaluation mandatory")
    private OfferedCourseEvaluationDTO offeredCourseEvaluation;
}
