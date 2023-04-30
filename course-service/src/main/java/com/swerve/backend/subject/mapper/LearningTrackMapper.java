package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.LearningTrackDTO;
import com.swerve.backend.subject.model.LearningTrack;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface LearningTrackMapper extends BaseMapper<LearningTrack, LearningTrackDTO,Long> {

    @Override
    @Named("default")
    LearningTrackDTO toDTO(LearningTrack learningTrack);

    @Override
    @Named("default")
    LearningTrack toModel(LearningTrackDTO learningTrackDTO);
}
