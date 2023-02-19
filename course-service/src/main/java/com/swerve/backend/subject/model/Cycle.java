package com.swerve.backend.subject.model;


import com.swerve.backend.shared.model.BaseEntity;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cycle extends BaseEntity<Long>{

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;
    @OneToMany(mappedBy = "cycle")
    private Set<OfferedCourse> offeredCourses=new HashSet<>();

}
