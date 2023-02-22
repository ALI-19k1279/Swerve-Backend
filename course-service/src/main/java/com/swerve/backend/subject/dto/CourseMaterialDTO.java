package com.swerve.backend.subject.dto;

import com.swerve.backend.shared.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseMaterialDTO extends BaseDTO<Long> {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Description is mandatory")
    private String description;
    @NotBlank(message = "Resource URL is mandatory")
    private String resourceUrl;
    @NotBlank(message = "Publication Date is mandatory")
    private LocalDateTime publicationDate;
    @NotBlank(message = "TeacherID is mandatory")
    private Long teacherID;

}
