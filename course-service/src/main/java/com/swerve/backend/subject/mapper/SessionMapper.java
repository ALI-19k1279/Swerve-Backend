package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.SessionDTO;
import com.swerve.backend.subject.model.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SessionMapper extends BaseMapper<Session, SessionDTO,Long> {

    SessionDTO toDTO(Session session);

    Session toModel(SessionDTO sessionDTO);
}
