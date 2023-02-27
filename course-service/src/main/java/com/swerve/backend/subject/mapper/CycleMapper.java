package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.CycleDTO;
import com.swerve.backend.subject.model.Cycle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CycleMapper extends BaseMapper<Cycle, CycleDTO,Long> {

    CycleDTO toDTO(Cycle cycle);

    Cycle toModel(CycleDTO cycleDTO);
}
