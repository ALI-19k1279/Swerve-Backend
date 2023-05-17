package com.swerve.backend.auth.service;

import com.swerve.backend.auth.mapper.RoleMapper;
import com.swerve.backend.auth.model.Role;
import com.swerve.backend.auth.model.Role_Features;
import com.swerve.backend.auth.model.System_Features;
import com.swerve.backend.auth.repository.RoleRepository;
import com.swerve.backend.shared.dto.RoleDTO;
import com.swerve.backend.shared.exception.NotFoundException;
import com.swerve.backend.shared.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static com.swerve.backend.shared.security.SecurityUtils.FOLDER_PATH;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RoleService extends BaseService<Role, RoleDTO, Long> {
    private final RoleRepository repository;
    private final RoleMapper mapper;

    public RoleService(RoleRepository repository, RoleMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }


    public void customizeUserRoles(String authority,List<String> features,Long userId,String username,String password,String
            portal) {
        Role role = repository.findByAuthority(authority);
        if (role == null) {
            role = new Role();
            role.setAuthority(authority);
            repository.save(role);
        }
        List<Long> systemFeatureIds = getSystemFeatures(features);
        Long roleId=role.getId();
        for (Long featureId : systemFeatureIds) {
            Role_Features roleFeature = new Role_Features();
            repository.insertRoleFeature(roleId, featureId);
        }
        repository.insertUser(userId,username,portal,
                password);
        repository.inserUserRole(userId, roleId);
    }
    private List<Long> getSystemFeatures(List<String> features) {

        List<Long> systemFeatureIds=new ArrayList<>();
        for(String feature : features){
            System.out.println(feature);
            System.out.println(repository.findByDescription(feature));
            systemFeatureIds.add(repository.findByDescription(feature));
        }
        return systemFeatureIds;
    }
}
