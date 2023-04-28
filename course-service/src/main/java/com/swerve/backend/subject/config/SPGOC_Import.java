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
public class SPGOC_Import extends BaseDTO<Long>  {
    @NotBlank(message = "Student is mandatory")
    private Long student_id;

    @NotBlank(message = "isEnrolled is mandatory")
    private Boolean is_enrolled;

    @NotBlank(message = "Group is mandatory")
    private Long group_id;

    @NotBlank(message = "Offered Course is mandatory")
    private Long offered_course_id;




}
