package com.swerve.backend.subject.dto;

import com.swerve.backend.shared.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubjectMaterialDTO extends BaseDTO<Long> {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Resource URL is mandatory")
    private String resourceUrl;

    @NotNull(message = "Publication date is mandatory")
    private LocalDateTime publicationDate;

    private TeacherDTO teacher;

    @NotBlank(message = "Description is mandatory")
    private SubjectDTO subject;
}
