package com.swerve.backend.userservice.dto;

import com.swerve.backend.shared.dto.BaseDTO;
import com.swerve.backend.shared.dto.UserDTO;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentDTO extends BaseDTO<Long> {
    @NotNull(message = "User is mandatory")
    private UserDTO user;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotBlank(message = "Index is mandatory")
    @Size(max = 45, message = "Index can't be longer than 45 characters")
    private String index;

    @NotNull(message = "Year of enrollment is mandatory")
    private String yearOfEnrollment;

//    @NotNull(message = "Year of enrollment is mandatory")
//    private String user_id;
    public static String[] getStudentFields(){
        return new String[] {"firstName","lastName","index","yearOfEnrollment","user_id"};
    }
}
