package com.swerve.backend.subject.repository;

import com.swerve.backend.shared.repository.BaseRepository;
import com.swerve.backend.subject.model.Course;
import com.swerve.backend.subject.model.CourseMaterial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseMaterialRepository extends BaseRepository<CourseMaterial,Long> {

    @Override
    @Query(
            "select x from #{#entityName} x where x.deleted = false "
                    + "and (cast(x.id as string) like :search "
                    + "or x.name like :search or x.description like :search or cast(x.publicationDate as string) like :search)")
    Page<CourseMaterial> findContaining(Pageable pageable, String search);

    @Query(
            "select x from #{#entityName} x where x.deleted = false and x.offeredCourse.id = :courseId "
                    + "and (cast(x.id as string) like :search "
                    + "or x.name like :search or x.description like :search or cast(x.publicationDate as string) like :search)")
    Page<CourseMaterial> findByCourseIdContaining(
            Long courseId, Pageable pageable, String search);

    List<CourseMaterial> findByOfferedCourseAndDeletedFalseOrderByPublicationDateDesc(Long courseId);
}
