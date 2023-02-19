package com.swerve.backend.subject.model;

import com.swerve.backend.shared.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class PreRequisite extends BaseEntity<Long> {

    @Column(nullable = false)
    private String code;

    @ManyToOne(optional = false)
    private Course preReqIs;

    @ManyToOne(optional = false)
    private Course preReqFor;


}
