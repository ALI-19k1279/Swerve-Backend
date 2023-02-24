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
public class    Course extends BaseEntity<Long> {

    @Column(nullable = false)
    private String courseCode;
    @Column(nullable = false)
    private String title;

    @Column()
    private String shortDescription;

    @Column(nullable = false)
    private String credits;


    @OneToMany(mappedBy = "preReqIs")
    private Set<PreRequisite> preReqIs=new HashSet<>();

    @OneToMany(mappedBy = "preReqFor")
    private Set<PreRequisite> preReqFor=new HashSet<>();

//    @OneToMany(mappedBy = "course")
//    private Set<CourseModule> courseModule=new HashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name="learningTrack_ID", nullable=false)
    private LearningTrack learningTrack;



    //    @Column(nullable = false)
//    private boolean isOffered;
//    @Lob
//    @Column(nullable = false)
//    private String syllabus;
//
//    @Column(nullable = false)
//    private Integer semester;
//
//    /*@Column(nullable = false)
//    private Integer ects;
//    */

//    @Column(nullable = false)
//    private Long studyProgramId;
//
//    @Column(nullable = false)
//    private Long professorId;
//
//    @Column(nullable = false)
//    private Long assistantId;
//
//    @OneToMany(mappedBy = "course")
//    private Set<StudentsPerGroup_OfferedCourse> subjectEnrollments = new HashSet<>();
//
//    @OneToMany(mappedBy = "course")
//    private Set<SubjectNotification> subjectNotifications = new HashSet<>();
//
//    @OneToMany(mappedBy = "course")
//    private Set<CourseMaterial> subjectMaterials = new HashSet<>();

//    @OneToMany(mappedBy = "course")
//    private Set<LearningTrack> subjectTerms = new HashSet<>();
}
