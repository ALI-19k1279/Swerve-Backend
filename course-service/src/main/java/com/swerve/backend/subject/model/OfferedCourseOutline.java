package com.swerve.backend.subject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swerve.backend.shared.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OfferedCourseOutline extends BaseEntity<Long> {

    @ManyToOne(optional = false)
    @JoinColumn(name="offeredCourse_ID", nullable=false)
    @JsonIgnore
    private OfferedCourse offeredCourse;

    private int sequenceNumber;

    private String description;


}
