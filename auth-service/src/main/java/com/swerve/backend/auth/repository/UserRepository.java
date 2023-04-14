package com.swerve.backend.auth.repository;

import com.swerve.backend.auth.model.User;
import com.swerve.backend.shared.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    @Override
    @Query(
            "select x from #{#entityName} x where x.deleted = false "
                    + "and (cast(x.id as string) like :search or x.username like :search)")
    Page<User> findContaining(Pageable pageable, String search);

    Optional<User> findByUsername(String username);

    @Query(value = "select s.description from system_features s, role_features r, role ro where ro.authority \n" +
            "in :authorities and ro.id=r.role_id and s.id=r.system_feature_id",nativeQuery = true)
    List<String> findByRolesAuthorityIn(List<String> authorities);
}
