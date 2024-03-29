package com.swerve.backend.subject.dto;

import com.swerve.backend.shared.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LearnerEvaluationDTO extends BaseDTO<Long> {

    private String title;
    private int marksObtained;
    private String type;
    private int totalMarks;
    private String publicationDate;
    private String deadlineDate;

}
