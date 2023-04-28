package com.swerve.backend.subject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class OfferedCourse extends BaseEntity<Long>{

    @ManyToOne(optional = false)

    private Course courseID;
    @Column()
    private int fee;

    @OneToMany
    private Set<CourseModule> courseModules=new HashSet<>();


    @Column()
    private String postsAndAnnouncements;

    @Column(nullable = false)
    private String instructorId;

    @ManyToOne(optional = false)
    @JoinColumn(name="cycle_ID", nullable=false)
    private Cycle cycle;

    @OneToMany
    private Set<CourseMaterial> courseMaterials=new HashSet<>();

    @OneToMany
    private Set<OfferedCourseOutline> offeredCourseOutlines=new HashSet<>();

}
