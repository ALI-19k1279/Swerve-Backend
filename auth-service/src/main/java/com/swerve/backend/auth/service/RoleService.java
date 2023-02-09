package com.swerve.backend.auth.service;

import com.swerve.backend.auth.mapper.RoleMapper;
import com.swerve.backend.auth.model.Role;
import com.swerve.backend.auth.repository.RoleRepository;
import com.swerve.backend.shared.dto.RoleDTO;
import com.swerve.backend.shared.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseService<Role, RoleDTO, Long> {
    private final RoleRepository repository;
    private final RoleMapper mapper;

    public RoleService(RoleRepository repository, RoleMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }
}
