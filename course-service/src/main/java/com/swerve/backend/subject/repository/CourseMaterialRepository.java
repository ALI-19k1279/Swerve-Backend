package com.swerve.backend.subject.repository;

import com.swerve.backend.shared.repository.BaseRepository;
import com.swerve.backend.subject.model.Course;
import com.swerve.backend.subject.model.CourseMaterial;
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
public interface CourseMaterialRepository extends BaseRepository<CourseMaterial,Long> {

    Optional<CourseMaterial> findById(Long id);


    List<CourseMaterial> findByTeacherIdAndOfferedCourseId(Long teacherId, Long offeredCourseId);

    @Modifying
    @Query(
            value =
                    "insert into course_material (name, description, resource_url, publication_date, teacher_id, offered_course_id) " +
                            "values (:name, :description, :resourceUrl, :publicationDate, :teacher, :offeredCourse)",
            nativeQuery = true)
    void insertCourseMaterial(@Param("name") String name, @Param("description") String description,
                      @Param("resourceUrl") String resourceUrl, @Param("publicationDate") LocalDateTime publicationDate,
                      @Param("teacher") Long teacher,
                        @Param("offeredCourse") Long offeredCourse);

//    @Override
//    @Query(
//            "select x from #{#entityName} x where x.deleted = false "
//                    + "and (cast(x.id as string) like :search "
//                    + "or x.name like :search or x.description like :search or cast(x.publicationDate as string) like :search)")
//    Page<CourseMaterial> findContaining(Pageable pageable, String search);
//
//    @Query(
//            "select x from #{#entityName} x where x.deleted = false and x.offeredCourse.id = :courseId "
//                    + "and (cast(x.id as string) like :search "
//                    + "or x.name like :search or x.description like :search or cast(x.publicationDate as string) like :search)")
//    Page<CourseMaterial> findByCourseIdContaining(
//            Long courseId, Pageable pageable, String search);
//
//    List<CourseMaterial> findByOfferedCourseAndDeletedFalseOrderByPublicationDateDesc(Long courseId);
}
