package com.swerve.backend.subject.model;

import com.swerve.backend.shared.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Lob;
//import javax.persistence.ManyToOne;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseMaterial extends BaseEntity<Long> {
    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String resourceUrl;

    @Column(nullable = false)
    private LocalDateTime publicationDate;

    @Column(nullable = false)
    private Long teacherId;

    @ManyToOne(optional = false)
    private Course course;
}
