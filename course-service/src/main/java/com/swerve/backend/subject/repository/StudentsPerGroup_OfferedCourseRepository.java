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

@Repository
public interface StudentsPerGroup_OfferedCourseRepository extends BaseRepository<StudentsPerGroup_OfferedCourse,Long> {

    List<StudentsPerGroup_OfferedCourse> findByStudentIdAndDeletedFalse(Long id);

    //select * from offered_course oc where oc.id in (select offered_course_id from students_per_group_offered_course where group_id=1 )
    @Query("Select oc from OfferedCourse oc where oc.id in "
    +"(select spgoc.offeredCourse from StudentsPerGroup_OfferedCourse spgoc where spgoc.group.id=:id)")
    List<OfferedCourse> findOfferedCoursesByGroupId(Long id);

    @Query("Select oc from OfferedCourse oc where oc.id in "
            +"(select spgoc.offeredCourse from StudentsPerGroup_OfferedCourse spgoc where spgoc.studentId=:id)")
    List<OfferedCourse> findOfferedCoursesByStudentId(Long id);

    @Query("Select oc from OfferedCourse oc where oc.teacherId=:id")
    List<OfferedCourse> findOfferedCoursesByTeacherId(Long id);

    @Query(value="select title as evaluationTitle" +
            ",type as evaluationType,marks_obtained as marksObtained" +
            ",total_marks as totalMarks " +
            "from offered_course_evaluation_item o, " +
            "students_per_group_offered_course s, " +
            "offered_course_evaluation e where " +
            "o.spg_id=s.id and e.oce_id=o.id and e.studentid=:stdid " +
            "and s.group_id=:gid and s.offered_course_id=:ocid " +
            "group by title,type,marks_obtained,total_marks;",nativeQuery = true)
    List<String> findEvaluationItemsByStudentId(Long stdid, Long gid, Long ocid);
    @Query(value = "select title,type,MAX(marks_obtained),MIN(marks_obtained),AVG(marks_obtained) from " +
            "offered_course_evaluation_item o,students_per_group_offered_course s," +
            "offered_course_evaluation e where s.id=o.spg_id and e.oce_id=o.id and s.offered_course_id=:id " +
            "group by title,type",nativeQuery = true)
    List<String> findMaxMinAvgbyOfferedCourseID(Long id);


//
//    @Query("select oc from  OfferedCourseEvaluation oc")
//    List<Integer> findStudentsByGroupID();

}
