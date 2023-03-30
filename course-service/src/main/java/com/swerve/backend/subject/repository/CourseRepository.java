package com.swerve.backend.subject.repository;

import com.swerve.backend.shared.repository.BaseRepository;
import com.swerve.backend.subject.model.Course;
import com.swerve.backend.subject.model.LearningTrack;
import com.swerve.backend.subject.model.OfferedCourse;
import com.swerve.backend.subject.model.PreRequisite;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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


    @Query("select oc from Course c,OfferedCourse oc where c.id=oc.courseID")
    List<OfferedCourse> GetAllOfferedCourses();

    @Query("select o from StudentsPerGroup_OfferedCourse s," +
            "OfferedCourse o where o.id=s.offeredCourse.id and s.studentId=:id")
    List<OfferedCourse> GetAllOfferedCoursesByStudentID(Long id);

    @Query("select o from CourseMaterial o " +
            "where o.offeredCourse=:id")
    List<OfferedCourse> GetOfferedCourseMaterialByID(Long id);


    List<Course> findAll();
    @Modifying
    @Query(
            value =
                    "insert into Course (course_code, credits, short_description, title,learning_track_id) " +
                            "values (:course_code, :credits, :short_description, :title,:learning_track_id)",
            nativeQuery = true)
    void insertCourse(@Param("course_code") String course_code, @Param("credits") String credits,
                    @Param("short_description") String short_description, @Param("title") String title,
                      @Param("learning_track_id") Long learning_track_id);

//    List<Course> findByProfessorIdOrAssistantIdAndDeletedFalseOrderBySemesterAscNameAsc(
//            Long professorId, Long assistantId);
//
//    List<Course> findBySubjectEnrollmentsStudentIdAndDeletedFalse(Long studentId);
}
