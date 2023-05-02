package com.swerve.backend.subject.repository;

import com.swerve.backend.shared.repository.BaseRepository;
import com.swerve.backend.subject.dto.CourseOutlineDTO;
import com.swerve.backend.subject.model.OfferedCourseOutline;

import java.util.List;
import java.util.Optional;

public interface OfferedCourseOutlineRepository extends BaseRepository<OfferedCourseOutline, Long> {

    List<OfferedCourseOutline> findByOfferedCourseId(Long offeredCourseId);
    Optional<OfferedCourseOutline> findByOfferedCourseIdAndWeek(Long ocid,int week);
}
