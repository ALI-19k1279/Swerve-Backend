package com.swerve.backend.auth.service;

import com.swerve.backend.auth.mapper.RoleMapper;
import com.swerve.backend.auth.model.Role;
import com.swerve.backend.auth.repository.RoleRepository;
import com.swerve.backend.shared.dto.RoleDTO;
import com.swerve.backend.shared.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static com.swerve.backend.shared.security.SecurityUtils.FOLDER_PATH;

@Service
public class RoleService extends BaseService<Role, RoleDTO, Long> {
    private final RoleRepository repository;
    private final RoleMapper mapper;

    public RoleService(RoleRepository repository, RoleMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    public void createRole(String role) {

        repository.insertRole(role);
    }

    public Role findRole(String role) {

        return (Role) repository.findByAuthority(role);
    }
}
