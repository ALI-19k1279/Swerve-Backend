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

    @Modifying
    @Query(value = "INSERT INTO _user( " +
            " id, deleted, account_non_expired, account_non_locked, credentials_non_expired, enabled, password, portal, username) " +
            " VALUES ( :id,false, true, true, true, true, :password, :portal, :username)",nativeQuery = true)
    void insertUser(Long id,String username,String portal,String password);

    @Modifying
    @Query(value = "INSERT INTO user_role( " +
            " user_id, role_id)" +
            " VALUES (:userId, :roleId)",nativeQuery = true)
    void inserUserRole(Long userId,Long roleId);

    //INSERT INTO public._user(
    //	id, deleted, account_non_expired, account_non_locked, credentials_non_expired, enabled, password, portal, username)
    //	VALUES ( 134,false, true, true, true, true, '$2a$10$yLjYHMiWp.4srkrTErsb1u1y98wSrdf9T70TtZzqsBEBSSH9hZKuW', 'learner', 'swerve134');

}