package com.swerve.backend.subject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swerve.backend.shared.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OfferedCourseOutline extends BaseEntity<Long> {

    private Long teacherId;
    private int week;

    @ManyToOne(optional = false)
    @JoinColumn(name="offeredCourse_ID", nullable=false)
    private OfferedCourse offeredCourse;

    @ElementCollection
    @CollectionTable(name = "outline_topics", joinColumns = @JoinColumn(name = "outline_id"))
    @OrderColumn(name = "sequence_number")
    private List<String> topics = new ArrayList<>();

}

