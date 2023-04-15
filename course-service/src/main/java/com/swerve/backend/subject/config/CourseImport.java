package com.swerve.backend.subject.config;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseImport {

    private String course_code;
    private String credits;
    private String short_description;
    private String title;
    private Long learning_track_id;
}
