package com.swerve.backend.userservice.config;


import com.swerve.backend.shared.dto.UserDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentImport {

    private Long user_id;
    private String first_name;
    private String last_name;
    private String s_index;
    private Integer year_of_enrollment;
}
