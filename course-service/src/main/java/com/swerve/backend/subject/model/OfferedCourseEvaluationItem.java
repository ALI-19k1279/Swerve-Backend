package com.swerve.backend.subject.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swerve.backend.shared.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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

    @Column(nullable = false)
    private LocalDateTime publicationDate;

    @Column(nullable = false)
    private LocalDateTime deadlineDate;

    @Column(nullable = false)
    private Long groupID;

    @Column(nullable = false)
    private Long offeredCourseID;

    @Column(nullable = false)
    private Long teacherID;

//    @ManyToOne(optional = false)
//    @JoinColumn(name="SPG_ID", nullable=false)
//    private StudentsPerGroup_OfferedCourse studentsPerGroup_OfferedCourse;

    @OneToMany
    Set<OfferedCourseEvaluation> offeredCourseEvaluations=new HashSet<>();

}
