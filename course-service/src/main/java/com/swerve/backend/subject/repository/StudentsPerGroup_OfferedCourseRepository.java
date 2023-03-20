package com.swerve.backend.subject.repository;

import com.swerve.backend.shared.repository.BaseRepository;
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

    @Query("select ev from OfferedCourseEvaluation ev where ev.studentID=:id")
    List<OfferedCourseEvaluation> findEvaluationItemsByStudentId(Long id);
//    @Query("select ev from OfferedCourseEvaluation ev where ev.studentID=:id " +
//            "and ev.offeredCourse.id=:crcid")
//    List<OfferedCourseEvaluation> findEvaluationItemsByStudentIdandOfferedCourseID(Long id,Long crcid);
//
//    @Query("select oc from  OfferedCourseEvaluation oc")
//    List<Integer> findStudentsByGroupID();

}
