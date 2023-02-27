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
public class PreRequisiteDTO extends BaseDTO<Long> {
    @NotBlank(message = "Code is mandatory")
    private String code;
    @NotBlank(message = "PreRequisiteIs is mandatory")
    private CourseDTO preReqIs;
    @NotBlank(message = "PreRequisiteFor is mandatory")
    private CourseDTO preReqFor;


}
