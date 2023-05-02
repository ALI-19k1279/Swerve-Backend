package com.swerve.backend.subject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swerve.backend.shared.dto.BaseDTO;
import com.swerve.backend.subject.model.OfferedCourse;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseOutlineDTO extends BaseDTO<Long> {

    private Long offeredCourseId;
    private List<WeeklyCourseOutline> courseOutline;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class WeeklyCourseOutline {
        private int week;
        private List<String> topics;

        @JsonIgnore
        public String getTopic(int index) {
            return index == 0 ? "[placeholder]" : topics.get(index - 1);
        }
    }
}
