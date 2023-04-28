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
public class SPGOC_DTO extends BaseDTO<Long>  {
    @NotBlank(message = "Student is mandatory")
    private String student_id;

    @NotBlank(message = "Offered Course is mandatory")
    private String offered_course_id;

    @NotBlank(message = "Group is mandatory")
    private String group_id;

    @NotBlank(message = "isEnrolled is mandatory")
    private String is_enrolled;
    public static String[] getEnrollmentFields(){
        return new String[] {"student_id","is_enrolled","group_id","offered_course_id"};
    }
}
