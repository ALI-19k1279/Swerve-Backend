package com.swerve.backend.userservice.config;

import com.swerve.backend.userservice.dto.StudentDTO;
import com.swerve.backend.userservice.dto.StudentImportDTO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class StudentProcessor implements ItemProcessor<StudentImportDTO, StudentImport> {
    @Override
    public StudentImport process(StudentImportDTO studentDTO) throws Exception {
        final String first_name=studentDTO.getFirst_name();
        final String last_name=studentDTO.getLast_name();
        final String index= studentDTO.getS_index();
        final Integer year_of_enrollment=Integer.parseInt(studentDTO.getYear_of_enrollment());
        final Long user_id=Long.parseLong(studentDTO.getUser_id());

        final StudentImport transformedDTO = new StudentImport(user_id,first_name,last_name,index,year_of_enrollment);

        log.info("Converting (" + studentDTO + ") into (" + transformedDTO + ")");
        return transformedDTO;

    }
}
