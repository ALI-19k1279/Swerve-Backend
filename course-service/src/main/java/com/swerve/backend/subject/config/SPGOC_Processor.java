package com.swerve.backend.subject.config;

import com.swerve.backend.subject.dto.CourseDTO;
import com.swerve.backend.subject.dto.SPGOC_DTO;
import com.swerve.backend.subject.dto.SPGOC_Import;
import org.springframework.batch.item.ItemProcessor;

public class SPGOC_Processor  implements ItemProcessor<SPGOC_DTO, SPGOC_Import> {
    @Override
    public SPGOC_Import process(SPGOC_DTO spgocDto) throws Exception {
        final Boolean is_enrolled= Boolean.parseBoolean(spgocDto.getIs_enrolled());
       final Long student_id=Long.parseLong(spgocDto.getStudent_id());
       final Long group_id=Long.parseLong(spgocDto.getGroup_id());
       final Long offered_course_id=Long.parseLong(spgocDto.getOffered_course_id());
        System.out.println(student_id+" "+group_id+" "+offered_course_id+" "+is_enrolled);
        return new SPGOC_Import(student_id,is_enrolled,group_id,offered_course_id);
    }
}
