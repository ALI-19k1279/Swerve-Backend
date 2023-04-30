package com.swerve.backend.auth.mapper;

import com.swerve.backend.auth.model.Role;
import com.swerve.backend.shared.dto.RoleDTO;
import com.swerve.backend.shared.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RoleMapper extends BaseMapper<Role, RoleDTO, Long> {
    @Override
    @Named("default")
    RoleDTO toDTO(Role cycle);

    @Override
    @Named("default")
    Role toModel(RoleDTO cycleDTO);
}
