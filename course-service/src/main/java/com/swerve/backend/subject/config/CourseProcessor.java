package com.swerve.backend.subject.config;

import com.swerve.backend.subject.dto.CourseDTO;
import com.swerve.backend.subject.model.Course;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class CourseProcessor  implements ItemProcessor<CourseDTO, CourseImport> {

    @Override
    public CourseImport process(final CourseDTO courseDTO) throws Exception {

        final String course_code=courseDTO.getCourse_code();
        final String credits=courseDTO.getCredits();
        final String short_description=courseDTO.getShort_description();
        final String title=courseDTO.getTitle();
        long learning_track_id;
        try {
            learning_track_id = Long.parseLong(courseDTO.getLearning_track_id());
        } catch (NumberFormatException e) {
            learning_track_id = 0; // default value
        }
        final CourseImport transformedDTO = new CourseImport(course_code,credits,short_description,
                title,learning_track_id);

        log.info("Converting (" + courseDTO + ") into (" + transformedDTO + ")");
        return transformedDTO;
    }
}
