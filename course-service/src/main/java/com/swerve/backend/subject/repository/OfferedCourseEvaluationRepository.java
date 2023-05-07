package com.swerve.backend.subject.repository;

import com.swerve.backend.shared.repository.BaseRepository;
import com.swerve.backend.subject.dto.LearnerEvaluationDTO;
import com.swerve.backend.subject.model.OfferedCourseEvaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferedCourseEvaluationRepository extends BaseRepository<OfferedCourseEvaluation,Long> {

    @Query(value="SELECT ocei.title, oce.marks_obtained, ocei.type, ocei.total_marks,ocei.publication_date,ocei.deadline_date" +
            " FROM offered_course_evaluation_item ocei" +
            " JOIN offered_course_evaluation oce ON oce.oce_id = ocei.id" +
            " WHERE oce.studentid = :studentID AND ocei.offered_courseid= :offeredCourseID",nativeQuery = true)
    List<String> findEvaluationItemsByStudentIdAndOfferedCourseId(@Param("studentID") Long studentID, @Param("offeredCourseID") Long offeredCourseID);

    @Query(value="SELECT ocei.title,ocei.id," +
            " MAX(oce.marks_obtained) AS max_marks," +
            " MIN(oce.marks_obtained) AS min_marks," +
            " AVG(oce.marks_obtained) AS avg_marks" +
            " FROM offered_course_evaluation_item ocei" +
            " JOIN offered_course_evaluation oce ON oce.oce_id = ocei.id" +
            " WHERE ocei.offered_courseid = :offeredCourseID" +
            " GROUP BY ocei.title,ocei.id",nativeQuery = true)
    List<String> findEvaluationItemsMinMaxAverageByTeacherIdAndOfferedCourseId(@Param("offeredCourseID") Long offeredCourseID);
}
