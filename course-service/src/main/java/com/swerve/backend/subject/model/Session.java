package com.swerve.backend.subject.model;

import com.swerve.backend.shared.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
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
public class Session extends BaseEntity<Long>{

    @ManyToOne(optional = false)
    private OfferedCourse offeredCourse;

    @ManyToOne(optional = false)
    private Group group;

    @Lob
    @Column(nullable = false)
    private String objectives;

    @Column(nullable = false)
    private String mode;
}
