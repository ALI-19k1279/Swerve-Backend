package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.SessionDTO;
import com.swerve.backend.subject.model.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SessionMapper extends BaseMapper<Session, SessionDTO,Long> {

    @Override
    @Named("default")
    SessionDTO toDTO(Session session);

    @Override
    @Named("default")
    Session toModel(SessionDTO sessionDTO);
}
