package com.swerve.backend.userservice.dto;

import com.swerve.backend.shared.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubjectDTO extends BaseDTO<Long> {
    private String name;
    private String syllabus;
    private Integer semester;
    private Integer ects;
    private TeacherDTO professor;
    private TeacherDTO assistant;
}
