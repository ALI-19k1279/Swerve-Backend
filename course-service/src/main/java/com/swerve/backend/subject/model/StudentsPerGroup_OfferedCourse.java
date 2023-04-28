package com.swerve.backend.subject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentsPerGroup_OfferedCourse extends BaseEntity<Long> {
    @Column(nullable = false)
    private Long studentId;

    @ManyToOne(optional = false)
    @JoinColumn(name="offered_course_id", nullable=false)
    private OfferedCourse offeredCourse;

    @ManyToOne(optional = false)
    @JoinColumn(name="group_id", nullable=false)
    private Group group;

    @Column(nullable = false,columnDefinition = "boolean default true")
    private Boolean isEnrolled;

    @OneToMany
    private Set<OfferedCourseEvaluationItem> offeredCourseEvaluationItems=new HashSet<>();

}
