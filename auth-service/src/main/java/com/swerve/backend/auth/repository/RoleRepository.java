package com.swerve.backend.auth.repository;

import com.swerve.backend.auth.model.Role;
import com.swerve.backend.auth.model.System_Features;
import com.swerve.backend.shared.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {
    @Override
    @Query(
            "select x from #{#entityName} x where x.deleted = false "
                    + "and (cast(x.id as string) like :search or x.authority like :search)")
    Page<Role> findContaining(Pageable pageable, String search);

    @Query("Select sf.id from System_Features sf where sf.description=:subtaskname")
    Long findByDescription(@Param("subtaskname") String subtaskname);
    @Query("Select sf.id from System_Features sf where sf.id=:sysId")
    System_Features findSystemFeatureById(@Param("sysId") Long sysId);


    @Modifying
    @Query(value="Insert into role_features( role_id, system_feature_id)" +
            " Values (:roleid,:roleFeatureid) ",nativeQuery = true)
    void insertRoleFeature(Long roleid,Long roleFeatureid);

    Role findByAuthority(String authority);

}