package com.swerve.backend.subject.model;

import com.swerve.backend.shared.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentsPerGroup_OfferedCourse extends BaseEntity<Long> {
    @Column(nullable = false)
    private Long studentId;

    @ManyToOne(optional = false)
    private OfferedCourse offeredCourse;

    @ManyToOne(optional = false)
    private Group group;

    @Column(nullable = false,columnDefinition = "boolean default true")
    private Boolean isEnrolled;
}
