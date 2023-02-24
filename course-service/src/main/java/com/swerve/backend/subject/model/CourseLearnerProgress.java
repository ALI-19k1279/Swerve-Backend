package com.swerve.backend.subject.model;


import com.swerve.backend.shared.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseLearnerProgress extends BaseEntity<Long> {

    @Column(nullable = false)
    private String studentID;


    @Column(nullable = false)
    private boolean isDone;



    @ManyToOne(optional = false)
    @JoinColumn(name="courseModule_ID", nullable=false)
    private CourseModule courseModule;


}
