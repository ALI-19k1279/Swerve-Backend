package com.swerve.backend.subject.repository;

import com.swerve.backend.shared.repository.BaseRepository;
import com.swerve.backend.subject.model.Course;
import com.swerve.backend.subject.model.Cycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CycleRepository extends BaseRepository<Cycle,Long> {

    @Override
    @Query(
            "select x from #{#entityName} x where x.deleted = false "
                    + "and (cast(x.id as string) like :search or x.startDate like :search "
                    + "or x.endDate like :search )")
    Page<Cycle> findContaining(Pageable pageable, String search);
    List<Cycle> findByIdAndDeletedFalse(Long id);
}
