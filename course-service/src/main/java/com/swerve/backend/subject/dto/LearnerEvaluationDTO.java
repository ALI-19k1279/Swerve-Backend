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
public class LearnerEvaluationDTO extends BaseDTO<Long> {

    private String evaluationTitle;

    private String evaluationType;


    private int totalMarks;
    private int marksObtained;


    private int minMarks=0;

    private int maxMarks=0;

}
