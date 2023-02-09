package com.swerve.backend.auth.mapper;

import com.swerve.backend.auth.model.Role;
import com.swerve.backend.shared.dto.RoleDTO;
import com.swerve.backend.shared.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends BaseMapper<Role, RoleDTO, Long> {}
