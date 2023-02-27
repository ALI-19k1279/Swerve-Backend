package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.PreRequisiteDTO;
import com.swerve.backend.subject.model.PreRequisite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PreRequisiteMapper extends BaseMapper<PreRequisite, PreRequisiteDTO,Long> {

    PreRequisiteDTO toDTO(PreRequisite preRequisite);

    PreRequisite toModel(PreRequisiteDTO preRequisiteDTO);
}
