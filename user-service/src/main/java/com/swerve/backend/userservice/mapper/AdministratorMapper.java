package com.swerve.backend.userservice.mapper;

import com.swerve.backend.userservice.dto.AdministratorDTO;
import com.swerve.backend.userservice.model.Administrator;
import com.swerve.backend.shared.dto.UserDTO;
import com.swerve.backend.shared.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AdministratorMapper extends BaseMapper<Administrator, AdministratorDTO, Long> {
    @Override
    @Named("default")
    @Mapping(source = "userId", target = "user")
    AdministratorDTO toDTO(Administrator administrator);

    @Override
    @Named("default")
    @Mapping(source = "user.id", target = "userId")
    Administrator toModel(AdministratorDTO administratorDTO);

    UserDTO userDTOFromId(Long id);
}
