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
public class CourseLearnerProgressDTO extends BaseDTO<Long> {
    @NotBlank(message = "StudentID is mandatory")
    private String studentID;

    @NotBlank(message = "isDone is mandatory")
    private Boolean isDone;

}
