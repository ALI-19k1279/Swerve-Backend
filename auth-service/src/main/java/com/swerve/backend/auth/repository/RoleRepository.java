package com.swerve.backend.auth.repository;

import com.swerve.backend.auth.model.Role;
import com.swerve.backend.shared.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {
    @Override
    @Query(
            "select x from #{#entityName} x where x.deleted = false "
                    + "and (cast(x.id as string) like :search or x.authority like :search)")
    Page<Role> findContaining(Pageable pageable, String search);

    List<Role> findByAuthority(String role);

    @Modifying
    @Query(
            value =
                    "insert into role (role) " +
                            "values (:role)",
            nativeQuery = true)
    void insertRole(@Param("role") String role);
}
