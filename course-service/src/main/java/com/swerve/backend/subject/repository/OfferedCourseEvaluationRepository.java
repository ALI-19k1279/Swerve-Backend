package com.swerve.backend.subject.repository;

import com.swerve.backend.shared.repository.BaseRepository;
import com.swerve.backend.subject.dto.LearnerEvaluationDTO;
import com.swerve.backend.subject.model.OfferedCourseEvaluation;
import com.swerve.backend.subject.model.OfferedCourseEvaluationItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Modifying
    @Query(
            value = "INSERT INTO offered_course_evaluation_item (can_reattempt, groupid, offered_courseid," +
                    "passing_marks, publication_date, teacherid, title, total_marks," +
                    "type, resource_url, instructions, deadline_date) " +
                    "VALUES (:canReattempt,:groupId,:offeredCourseId,:passingMarks,:publicationDate," +
                    ":teacherId,:title,:totalMarks,:type,:resourceUrl,:instructions,:deadlineDate)",
            nativeQuery = true
    )
    void insertEvalautionItem(
            @Param("title") String title,
            @Param("type") String type,
            @Param("canReattempt") boolean canReattempt,
            @Param("totalMarks") int totalMarks,
            @Param("passingMarks") int passingMarks,
            @Param("publicationDate") LocalDateTime publicationDate,
            @Param("deadlineDate") LocalDateTime deadlineDate,
            @Param("groupId") Long groupId,
            @Param("offeredCourseId") Long offeredCourseId,
            @Param("teacherId") Long teacherId,
            @Param("resourceUrl") String resourceUrl,
            @Param("instructions") String instructions
    );
    @Modifying
    @Query(
            value = "UPDATE offered_course_evaluation_item SET deadline_date=:deadlineDate " +
                    "where id=:evalItemId",
            nativeQuery = true
    )
    void updateEvalautionItem(
            @Param("deadlineDate") LocalDateTime deadlineDate,
            @Param("evalItemId") Long evalItemId
    );
    @Query("Select oce from OfferedCourseEvaluationItem oce where oce.id=:evalItemId")
    Optional<OfferedCourseEvaluationItem> findItemById(@Param("evalItemId") Long id);

    @Modifying
    @Query("DELETE FROM OfferedCourseEvaluationItem oce WHERE oce.id = :evalItemId")
    void deleteItemById(@Param("evalItemId") Long id);

    @Query("Select oce from OfferedCourseEvaluation oce JOIN " +
            " OfferedCourseEvaluationItem ocei where oce.offeredCourse_EvaluationItem.id = ocei.id AND ocei.offeredCourseID=:offeredCourseId " +
            " AND ocei.groupID=:groupId AND ocei.id=:evalItemId"

    )
    List<OfferedCourseEvaluation> findOfferedCourseEvaluationByGroupIdAndOfferedCourseId(Long offeredCourseId,Long groupId,Long evalItemId);

    @Modifying
    @Query(value = "UPDATE offered_course_evaluation " +
            "SET marks_obtained = :mark " +
            "WHERE oce_id IN " +
            "(SELECT id FROM offered_course_evaluation_item WHERE offered_courseid = :offeredCourseId " +
            " And id=:evalItemId) " +
            "AND studentid = :studentId", nativeQuery = true)
    int gradeSubmissions(@Param("offeredCourseId") Long offeredCourseId,
                         @Param("studentId") Long studentId,
                         @Param("mark") Integer mark,
                         @Param("evalItemId") Long evalItemId);


    @Modifying
    @Query(value = "INSERT INTO offered_course_evaluation (studentid, resource_url, oce_id, no_of_attempts) " +
            "VALUES (:studentID, :filePath, :evalItemId, :attempts)", nativeQuery = true)
    void submitCourseWork(@Param("studentID") Long studentID,
                          @Param("filePath") String filePath,
                          @Param("evalItemId") Long evalItemId,
                          @Param("attempts") Long attempts);

}



