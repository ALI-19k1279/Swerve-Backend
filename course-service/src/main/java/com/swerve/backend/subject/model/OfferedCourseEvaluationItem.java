package com.swerve.backend.subject.model;


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
public class OfferedCourseEvaluationItem extends BaseEntity<Long>{

    @Column(nullable = false)
    private String title;

    //Attachments left

    @Column(nullable = false)
    public boolean canReattempt;

    @Column(nullable = false)
    private int totalMarks;


    @Column(nullable = false)
    private int passingMarks;

    @ManyToOne(optional = false)
    @JoinColumn(name="OfferedCourseEvaluation_ID", nullable=false)
    private OfferedCourseEvaluation offeredCourseEvaluation;

}
