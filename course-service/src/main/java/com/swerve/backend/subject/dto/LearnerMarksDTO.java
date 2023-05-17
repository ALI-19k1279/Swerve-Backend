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
public class LearnerMarksDTO extends BaseDTO<Long> {
    private Long evalItemId;
    private Long studentId;
    private Integer mark;
}
