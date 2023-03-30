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
    private String course_code;

    @NotBlank(message = "Credit is mandatory")
    private String credits;

    private String short_description;
    @NotBlank(message = "Course title is mandatory")
    private String title;

    @NotBlank(message = "Learning Track is mandatory")
    private String learning_track_id;

    //Detail: Failing row contains (10, f, null, 3, null, FSE, null).

}
