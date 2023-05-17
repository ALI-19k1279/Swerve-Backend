package com.swerve.backend.subject.dto;

import com.swerve.backend.shared.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseListingDTO extends BaseDTO<Long> {
    private Long id;
    private String courseCode;
    private String title;
    private String shortDescription;
    private String credits;
    private boolean offered;
}
