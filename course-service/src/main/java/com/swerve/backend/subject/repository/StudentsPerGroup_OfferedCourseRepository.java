package com.swerve.backend.subject.repository;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.swerve.backend.shared.repository.BaseRepository;
import com.swerve.backend.subject.dto.LearnerEvaluationDTO;
import com.swerve.backend.subject.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StudentsPerGroup_OfferedCourseRepository extends BaseRepository<StudentsPerGroup_OfferedCourse,Long> {

    List<StudentsPerGroup_OfferedCourse> findByStudentIdAndDeletedFalse(Long id);

    List<StudentsPerGroup_OfferedCourse> findByGroupIdAndOfferedCourseIdAndDeletedFalse(Long groupId, Long offeredCourseId);

    //select * from offered_course oc where oc.id in (select offered_course_id from students_per_group_offered_course where group_id=1 )
    @Query("Select oc from OfferedCourse oc where oc.id in "
    +"(select spgoc.offeredCourse from StudentsPerGroup_OfferedCourse spgoc where spgoc.group.id=:id)")
    List<OfferedCourse> findOfferedCoursesByGroupId(Long id);

    @Query("Select oc from OfferedCourse oc where oc.id in "
            +"(select spgoc.offeredCourse from StudentsPerGroup_OfferedCourse spgoc where spgoc.studentId=:id)")
    List<OfferedCourse> findOfferedCoursesByStudentId(Long id);

    @Query("Select oc from OfferedCourse oc where oc.teacherId=:id")
    List<OfferedCourse> findOfferedCoursesByTeacherId(Long id);

    @Query("Select gr from Group gr where gr.id in "
            +"(select spgoc.group.id from StudentsPerGroup_OfferedCourse spgoc where spgoc.offeredCourse.id=:id)")
    List<Group> findByOfferedCourseIdAndDeletedFalse(Long id);


    @Query("SELECT oce.offeredCourse_EvaluationItem.type, oce " +
            "FROM OfferedCourseEvaluationItem ocei " +
            "LEFT JOIN OfferedCourseEvaluation oce ON oce.offeredCourse_EvaluationItem = ocei " +
            "WHERE oce.studentID = :stdid AND ocei.offeredCourseID = :ocid " +
            "GROUP BY oce.offeredCourse_EvaluationItem.type,oce")
    Object[] findEvaluationItemsByStudentId(Long stdid, Long ocid);
    @Query("SELECT ocei.type, ocei " +
            "FROM OfferedCourseEvaluationItem ocei " +
            "WHERE ocei.teacherID = :teacherId AND ocei.offeredCourseID = :ocid " +
            "GROUP BY ocei.type,ocei")
    Object[] findEvaluationItemsByTeacherIdType(Long teacherId, Long ocid);
    @Query("SELECT ocei " +
            "FROM OfferedCourseEvaluationItem ocei " +
            "WHERE ocei.teacherID = :tid AND ocei.offeredCourseID = :ocid " )
    List<OfferedCourseEvaluationItem> findEvaluationItemsByTeacherId(Long tid, Long ocid);


    @Query(value = "select title,type,MAX(marks_obtained),MIN(marks_obtained),AVG(marks_obtained) from " +
            "offered_course_evaluation_item o,students_per_group_offered_course s," +
            "offered_course_evaluation e where s.id=o.spg_id and e.oce_id=o.id and s.offered_course_id=:id " +
            "group by title,type",nativeQuery = true)
    List<String> findMaxMinAvgbyOfferedCourseID(Long id);

    @Query("Select grp from Group grp")
    List<Group> findAllGroups();


//
//    @Query("select oc from  OfferedCourseEvaluation oc")
//    List<Integer> findStudentsByGroupID();

}
