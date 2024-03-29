package com.swerve.backend.subject.model;

import com.swerve.backend.shared.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Lob;
//import javax.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LearningTrack extends BaseEntity<Long> {
    @Column(nullable = false)
    private String name;

//    @Lob
    @Column(nullable = false)
    private String description;

//    @Column(nullable = false)
//    private LocalDateTime startTime;
//
//    @Column(nullable = false)
//    private LocalDateTime endTime;

//    @Column(nullable = false)
//    private Long teacherId;

    @OneToMany
    private Set<Course> course=new HashSet<>();
}
