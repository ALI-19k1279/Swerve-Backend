package com.swerve.backend.subject.dto;

import com.swerve.backend.shared.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LearningTrackDTO extends BaseDTO<Long>{
    @NotBlank(message = "Category name is mandatory")
    private String name;
    @NotBlank(message = "Category description is mandatory")
    private String description;
}
