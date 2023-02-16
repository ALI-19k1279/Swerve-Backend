package com.swerve.backend.subject.dto;

import com.swerve.backend.shared.dto.BaseDTO;
import com.swerve.backend.shared.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeacherDTO extends BaseDTO<Long> {
    private UserDTO user;
    private String firstName;
    private String lastName;
}
