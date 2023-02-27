package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.GroupDTO;
import com.swerve.backend.subject.model.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupMapper extends BaseMapper<Group, GroupDTO,Long> {

    GroupDTO toDTO(Group group);

    Group toModel(GroupDTO groupDTO);
}
