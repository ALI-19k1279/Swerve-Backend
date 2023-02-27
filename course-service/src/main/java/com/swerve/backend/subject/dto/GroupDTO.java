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
public class GroupDTO extends BaseDTO<Long> {
    @NotBlank(message = "Group code is mandatory")
    private String groupCode;
    @NotBlank(message = "Group name is mandatory")
    private String groupName;
}
