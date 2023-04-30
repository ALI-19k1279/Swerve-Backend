package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.GroupDTO;
import com.swerve.backend.subject.model.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface GroupMapper extends BaseMapper<Group, GroupDTO,Long> {

    @Override
    @Named("default")
    GroupDTO toDTO(Group group);

    @Override
    @Named("default")
    Group toModel(GroupDTO groupDTO);
}
