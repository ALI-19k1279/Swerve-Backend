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

    private Long learnerID;

    private String evaluationItem;

    private int totalMarks;
    private int marksObtained;

    private int noOfAttempts;

    private int minMarks;

    private int maxMarks;

}
