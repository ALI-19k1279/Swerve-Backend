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
public class SessionDTO extends BaseDTO<Long> {

    @NotBlank(message = "Offered Course is mandatory")
    private OfferedCourseDTO offeredCourse;
    @NotBlank(message = "Group is mandatory")
    private GroupDTO group;
    @NotBlank(message = "Obejctives are mandatory")
    private String objectives;
    @NotBlank(message = "Mode of conduct is mandatory")
    private String mode;
}
