package com.swerve.backend.subject.dto;

import com.swerve.backend.shared.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CycleDTO extends BaseDTO<Long> {
    @NotBlank(message = "Start date is mandatory")
    private Date startDate;

    @NotBlank(message = "End date is mandatory")
    private Date endDate;

}
