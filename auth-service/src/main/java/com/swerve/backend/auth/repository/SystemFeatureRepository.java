package com.swerve.backend.auth.repository;

import com.swerve.backend.auth.model.Role;
import com.swerve.backend.auth.model.System_Features;
import com.swerve.backend.shared.repository.BaseRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SystemFeatureRepository extends BaseRepository<System_Features, Long> {


}
