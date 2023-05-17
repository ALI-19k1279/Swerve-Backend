package com.swerve.backend.subject.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swerve.backend.shared.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OfferedCourseEvaluation extends BaseEntity<Long>{

    @ManyToOne(optional = false)
    @JoinColumn(name="OCE_ID", nullable=false)
    private OfferedCourseEvaluationItem offeredCourse_EvaluationItem;


    @Column(nullable = false)
    private Long studentID;

    @Column()
    private int marksObtained;

    @Column()
    private String resourceUrl;

    @Column()
    private int noOfAttempts;

//    @Column()
//    private DA submissionDate;
}
