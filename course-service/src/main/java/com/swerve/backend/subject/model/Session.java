package com.swerve.backend.subject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swerve.backend.shared.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Session extends BaseEntity<Long>{

    @ManyToOne(optional = false)
    @JoinColumn(name="offeredCourse_ID", nullable=false)
    private OfferedCourse offeredCourse;

    @ManyToOne(optional = false)
    @JoinColumn(name="group_ID", nullable=false)
    private Group group;

    @Column(nullable = false)
    private String objectives;

    @Column(nullable = false)
    private String mode;
}
