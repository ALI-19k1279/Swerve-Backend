package com.swerve.backend.subject.repository;

import com.swerve.backend.shared.repository.BaseRepository;
import com.swerve.backend.subject.model.OfferedCourseEvaluationItem;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferedCourseEvaluationItemRepository extends BaseRepository<OfferedCourseEvaluationItem,Long>{

    @Query("SELECT oce.offeredCourse_EvaluationItem.title, oce.marksObtained, oce.offeredCourse_EvaluationItem.type, oce.offeredCourse_EvaluationItem.totalMarks, oce.offeredCourse_EvaluationItem.publicationDate " +
            "FROM OfferedCourseEvaluation oce " +
            "WHERE oce.studentID = :studentID AND oce.offeredCourse_EvaluationItem.offeredCourseID = :offeredCourseID")
    List<Object[]> findEvaluationItemsByStudentIdAndOfferedCourseId(@Param("studentID") Long studentID, @Param("offeredCourseID") Long offeredCourseID);

}
