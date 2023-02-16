package com.swerve.backend.subject.client;

import com.swerve.backend.subject.dto.StudentDTO;
import com.swerve.backend.subject.dto.StudyProgramDTO;
import com.swerve.backend.subject.dto.TeacherDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

@FeignClient("faculty-service")
public interface FacultyFeignClient {
    @GetMapping("/study-programs/{id}")
    List<StudyProgramDTO> getStudyProgram(@PathVariable Set<Long> id);

    @GetMapping("/teachers/{id}")
    List<TeacherDTO> getTeacher(@PathVariable Set<Long> id);

    @GetMapping("/students/{id}")
    List<StudentDTO> getStudent(@PathVariable Set<Long> id);
}
