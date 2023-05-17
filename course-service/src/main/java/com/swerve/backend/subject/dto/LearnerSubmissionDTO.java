package com.swerve.backend.subject.dto;

import com.swerve.backend.shared.dto.BaseDTO;
import com.swerve.backend.subject.model.OfferedCourseEvaluationItem;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LearnerSubmissionDTO extends BaseDTO<Long> {

    private Long evalitemId;

    private Long studentID;

    private MultipartFile file;

    private int noOfAttempts;
}
