package com.swerve.backend.subject.repository;

import com.swerve.backend.shared.repository.BaseRepository;
import com.swerve.backend.subject.model.Course;
import com.swerve.backend.subject.model.LearningTrack;
import com.swerve.backend.subject.model.OfferedCourse;
import com.swerve.backend.subject.model.PreRequisite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CourseRepository extends BaseRepository<Course,Long> {
    @Override
    @Query(
            "select x from #{#entityName} x where x.deleted = false "
                    + "and (cast(x.id as string) like :search or x.courseCode like :search "
                    + "or x.title like :search or x.credits like :search )")
    Page<Course> findContaining(Pageable pageable, String search);

    List<Course> findByIdAndDeletedFalse(Long id);

    @Query("Select p from Course c,PreRequisite p where c.id=p.preReqFor "+
            "and c.id=:id")
    List<PreRequisite> findPreRequisiteByCourseID(Long id);

//    @Query("select x from learning_track x, course c where x.deleted=false " +
//            "and c.learning_track_id=x.id")
//    LearningTrack findLearningTrackByCourseId(Long id);

    @Query("select oc from Course c,OfferedCourse oc where c.id=oc.courseID")
    List<OfferedCourse> GetAllOfferedCourses();


    List<Course> findAll();

//    List<Course> findByProfessorIdOrAssistantIdAndDeletedFalseOrderBySemesterAscNameAsc(
//            Long professorId, Long assistantId);
//
//    List<Course> findBySubjectEnrollmentsStudentIdAndDeletedFalse(Long studentId);
}
