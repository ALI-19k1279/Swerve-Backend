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

@Table(name="_group")
public class Group extends BaseEntity<Long>{

    @Column(nullable = false)
    private String groupCode;

    @Column(nullable = false)
    private String groupName;

    @OneToMany
    private Set<StudentsPerGroup_OfferedCourse> studentsPerGroupOfferedCourses=new HashSet<>();

}
