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
    @JoinColumn(name="offeredCourse_ID", nullable=false)
    @JsonIgnore
    private OfferedCourse offeredCourse;

    @Column(nullable = false)
    private  String code;


    @Column(nullable = false)
    private String studentID;

    @Column()
    private int marksObtained;


    @Column()
    private int noOfAttempts;

    @OneToMany(mappedBy = "offeredCourseEvaluation")
    private Set<OfferedCourseEvaluationItem> offeredCourseEvaluationItems=new HashSet<>();
}
