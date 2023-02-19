package com.swerve.backend.subject.service;

import com.swerve.backend.shared.exception.ForbiddenException;
import com.swerve.backend.shared.exception.NotFoundException;
import com.swerve.backend.shared.service.ExtendedService;
import com.swerve.backend.subject.client.FacultyFeignClient;
import com.swerve.backend.subject.dto.SubjectDTO;
import com.swerve.backend.subject.mapper.SubjectMapper;
import com.swerve.backend.subject.model.Course;
import com.swerve.backend.subject.repository.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.swerve.backend.shared.security.SecurityUtils.*;

@Service
public class SubjectService extends ExtendedService<Course, SubjectDTO, Long> {
    private final SubjectRepository repository;
    private final SubjectMapper mapper;
    private final FacultyFeignClient facultyFeignClient;

    public SubjectService(
            SubjectRepository repository,
            SubjectMapper mapper,
            FacultyFeignClient facultyFeignClient) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
        this.facultyFeignClient = facultyFeignClient;
    }

    protected List<SubjectDTO> mapMissingValues(List<SubjectDTO> subjects) {
        map(
                subjects,
                SubjectDTO::getStudyProgram,
                SubjectDTO::setStudyProgram,
                facultyFeignClient::getStudyProgram);
        map(
                subjects,
                SubjectDTO::getProfessor,
                SubjectDTO::setProfessor,
                facultyFeignClient::getTeacher);
        map(
                subjects,
                SubjectDTO::getAssistant,
                SubjectDTO::setAssistant,
                facultyFeignClient::getTeacher);

        return subjects;
    }

    public List<SubjectDTO> findByStudyProgramId(Long id) {
        List<SubjectDTO> subjects =
                mapper.toDTO(
                        repository.findByStudyProgramIdAndDeletedFalseOrderBySemesterAscNameAsc(
                                id));
        var res=subjects.isEmpty() ? subjects : this.mapMissingValues(subjects);
        System.out.println(res);
        return subjects.isEmpty() ? subjects : this.mapMissingValues(subjects);
    }

    public List<SubjectDTO> findByTeacherId(Long id) {
        List<SubjectDTO> subjects =
                mapper.toDTO(
                        repository
                                .findByProfessorIdOrAssistantIdAndDeletedFalseOrderBySemesterAscNameAsc(
                                        id, id));
        return subjects.isEmpty() ? subjects : this.mapMissingValues(subjects);
    }

    public List<SubjectDTO> findByStudentId(Long id) {
        if (hasAuthority(ROLE_STUDENT) && !id.equals(getStudentId())) {
            throw new ForbiddenException("You are not allowed to view this student");
        }

        List<SubjectDTO> subjects =
                mapper.toDTO(repository.findBySubjectEnrollmentsStudentIdAndDeletedFalse(id));
        return subjects.isEmpty() ? subjects : this.mapMissingValues(subjects);
    }

    @Transactional
    public SubjectDTO updateSyllabus(Long id, String syllabus) {
        Course course =
                repository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException("Course not found"));

        if (hasAuthority(ROLE_TEACHER)
                && !course.getProfessorId().equals(getTeacherId())
                && !course.getAssistantId().equals(getTeacherId())) {
            throw new ForbiddenException("You are not allowed to update this course syllabus");
        }

        course.setSyllabus(syllabus);
        return mapper.toDTO(repository.save(course));
    }
}
