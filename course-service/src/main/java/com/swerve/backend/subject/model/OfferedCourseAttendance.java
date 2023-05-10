package com.swerve.backend.subject.model;


import com.swerve.backend.shared.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OfferedCourseAttendance  extends BaseEntity<Long> {
    @Column(nullable = false,columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private Date date;

    @Column(nullable = false,columnDefinition = "INTEGER DEFAULT 1")
    private int duration;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Long studentId;

    @ManyToOne
    @JoinColumn(name="group_id", nullable=false)
    private Group group;

    @ManyToOne
    @JoinColumn(name="oc_id", nullable=false)
    private OfferedCourse offeredCourse;

//    @PrePersist
//    protected void onCreate() {
//        if (date == null) {
//            date = Date.valueOf(LocalDate.now());
//        }
//    }
}
