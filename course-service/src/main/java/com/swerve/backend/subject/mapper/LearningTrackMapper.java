package com.swerve.backend.subject.mapper;

import com.swerve.backend.shared.mapper.BaseMapper;
import com.swerve.backend.subject.dto.LearningTrackDTO;
import com.swerve.backend.subject.model.LearningTrack;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LearningTrackMapper extends BaseMapper<LearningTrack, LearningTrackDTO,Long> {

    LearningTrackDTO toDTO(LearningTrack learningTrack);

    LearningTrack toModel(LearningTrackDTO learningTrackDTO);
}
