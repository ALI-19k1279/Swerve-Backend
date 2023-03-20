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
public class OfferedCourseEvaluationItem extends BaseEntity<Long>{

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String type;

    //Attachments left

    @Column(nullable = false)
    public boolean canReattempt;

    @Column(nullable = false)
    private int totalMarks;


    @Column(nullable = false)
    private int passingMarks;

    @ManyToOne(optional = false)
    @JoinColumn(name="SPG_ID", nullable=false)
    @JsonIgnore
    private StudentsPerGroup_OfferedCourse studentsPerGroup_OfferedCourse;

    @OneToMany(mappedBy = "offeredCourse_EvaluationItem")
    Set<OfferedCourseEvaluation> offeredCourseEvaluations=new HashSet<>();

}
