package com.swerve.backend.subject.repository;

import com.swerve.backend.shared.repository.BaseRepository;
import com.swerve.backend.subject.model.Course;
import com.swerve.backend.subject.model.StudentsPerGroup_OfferedCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentsPerGroup_OfferedCourseRepository extends BaseRepository<StudentsPerGroup_OfferedCourse,Long> {

    List<StudentsPerGroup_OfferedCourse> findByStudentIdAndDeletedFalse(Long id);

}
