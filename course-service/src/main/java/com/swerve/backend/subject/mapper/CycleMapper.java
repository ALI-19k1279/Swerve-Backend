package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.CycleDTO;
import com.swerve.backend.subject.model.Cycle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CycleMapper extends BaseMapper<Cycle, CycleDTO,Long> {

    @Override
    @Named("default")
    CycleDTO toDTO(Cycle cycle);

    @Override
    @Named("default")
    Cycle toModel(CycleDTO cycleDTO);
}
