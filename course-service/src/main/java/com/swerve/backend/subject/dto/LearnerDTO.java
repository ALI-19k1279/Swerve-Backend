package com.swerve.backend.subject.dto;

import com.swerve.backend.shared.dto.BaseDTO;
import com.swerve.backend.shared.dto.UserDTO;

public class LearnerDTO extends BaseDTO<Long> {
    private UserDTO user;
    private String firstName;
    private String lastName;
}
