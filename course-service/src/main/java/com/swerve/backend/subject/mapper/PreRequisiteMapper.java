package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.PreRequisiteDTO;
import com.swerve.backend.subject.model.PreRequisite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PreRequisiteMapper extends BaseMapper<PreRequisite, PreRequisiteDTO,Long> {

    @Override
    @Named("default")
    PreRequisiteDTO toDTO(PreRequisite preRequisite);

    @Override
    @Named("default")
    PreRequisite toModel(PreRequisiteDTO preRequisiteDTO);
}
