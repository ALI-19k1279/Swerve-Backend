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
public class EvaluationStatsDTO extends BaseDTO<Long> {
    private Long id;
    private String title;
    private Double maxMarks;
    private Double minMarks;
    private Double avgMarks;
}
