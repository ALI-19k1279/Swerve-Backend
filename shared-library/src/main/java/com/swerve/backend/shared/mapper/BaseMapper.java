package com.swerve.backend.shared.mapper;

import com.swerve.backend.shared.dto.BaseDTO;
import com.swerve.backend.shared.model.BaseEntity;
import org.mapstruct.IterableMapping;

import java.util.List;

public interface BaseMapper<Model extends BaseEntity<ID>, DTO extends BaseDTO<ID>, ID> {
    DTO toDTO(Model model);

    Model toModel(DTO DTO);

    @IterableMapping(qualifiedByName = "default")
    List<DTO> toDTO(List<Model> model);

    @IterableMapping(qualifiedByName = "default")
    List<Model> toModel(List<DTO> DTO);
}
