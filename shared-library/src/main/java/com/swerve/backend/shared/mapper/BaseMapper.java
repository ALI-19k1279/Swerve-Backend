package com.swerve.backend.shared.mapper;

import com.swerve.backend.shared.dto.BaseDTO;
import com.swerve.backend.shared.model.BaseEntity;

import java.util.List;

public interface BaseMapper<Model extends BaseEntity<ID>, DTO extends BaseDTO<ID>, ID> {
    DTO toDTO(Model model);

    Model toModel(DTO DTO);

    List<DTO> toDTO(List<Model> model);

    List<Model> toModel(List<DTO> DTO);
}
