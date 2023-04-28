package com.swerve.backend.userservice.dto;

import com.swerve.backend.shared.dto.BaseDTO;
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
public class StudentImportDTO extends BaseDTO<Long>{

        @NotNull(message = "User_id is mandatory")
        private String user_id;

        @NotBlank(message = "First name is mandatory")
        private String first_name;

        @NotBlank(message = "Last name is mandatory")
        private String last_name;

        @NotBlank(message = "Index is mandatory")
        @Size(max = 45, message = "Index can't be longer than 45 characters")
        private String s_index;

        @NotNull(message = "Year of enrollment is mandatory")
        private String year_of_enrollment;

        //    @NotNull(message = "Year of enrollment is mandatory")
//    private String user_id;
        public static String[] getStudentFields(){
            return new String[] {"first_name","s_index","last_name","user_id","year_of_enrollment"};
        }


}
