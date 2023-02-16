package com.swerve.backend.subject.service;

import com.swerve.backend.shared.exception.ForbiddenException;
import com.swerve.backend.shared.exception.NotFoundException;
import com.swerve.backend.shared.service.ExtendedService;
import com.swerve.backend.subject.client.FacultyFeignClient;
import com.swerve.backend.subject.dto.SubjectDTO;
import com.swerve.backend.subject.dto.SubjectTermDTO;
import com.swerve.backend.subject.dto.TeacherDTO;
import com.swerve.backend.subject.mapper.SubjectTermMapper;
import com.swerve.backend.subject.model.Subject;
import com.swerve.backend.subject.model.SubjectTerm;
import com.swerve.backend.subject.repository.SubjectTermRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.swerve.backend.shared.security.SecurityUtils.*;

@Service
public class SubjectTermService extends ExtendedService<SubjectTerm, SubjectTermDTO, Long> {
    private final SubjectTermRepository repository;
    private final SubjectTermMapper mapper;
    private final SubjectTermRepository subjectRepository;
    private final FacultyFeignClient facultyFeignClient;

    public SubjectTermService(
            SubjectTermRepository repository,
            SubjectTermMapper mapper,
            SubjectTermRepository subjectRepository,
            FacultyFeignClient facultyFeignClient) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
        this.subjectRepository = subjectRepository;
        this.facultyFeignClient = facultyFeignClient;
    }

    @Override
    @Transactional
    public SubjectTermDTO save(SubjectTermDTO subjectTermDTO) {
        if (hasAuthority(ROLE_TEACHER)) {
            TeacherDTO teacher = facultyFeignClient.getTeacher(Set.of(getTeacherId())).get(0);
            SubjectDTO subject = subjectTermDTO.getSubject();
            if (!subject.getProfessor().getId().equals(teacher.getId())
                    && !subject.getAssistant().getId().equals(teacher.getId())) {
                throw new ForbiddenException("You are not allowed to add terms to this subject");
            }

            if (subjectTermDTO.getTeacher() == null) {
                subjectTermDTO.setTeacher(teacher);
            }
        }

        return super.save(subjectTermDTO);
    }

    @Override
    @Transactional
    public void delete(Set<Long> id) {
        if (hasAuthority(ROLE_TEACHER)) {
            Long teacherId = getTeacherId();
            List<SubjectTerm> subjectTerms = (List<SubjectTerm>) repository.findAllById(id);
            boolean forbidden =
                    subjectTerms.stream()
                            .anyMatch(
                                    subjectTerm -> {
                                        Subject subject = subjectTerm.getSubject();
                                        return !subject.getProfessorId().equals(teacherId)
                                                && !subject.getAssistantId().equals(teacherId);
                                    });
            if (forbidden) {
                throw new ForbiddenException("You are not allowed to delete these subject terms");
            }
        }

        super.delete(id);
    }

    @Override
    protected List<SubjectTermDTO> mapMissingValues(List<SubjectTermDTO> subjectTerms) {
        map(
                subjectTerms,
                SubjectTermDTO::getTeacher,
                SubjectTermDTO::setTeacher,
                facultyFeignClient::getTeacher);

        return subjectTerms;
    }

    public List<SubjectTermDTO> findBySubjectId(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new NotFoundException("Subject not found");
        }

        List<SubjectTermDTO> subjectTerms =
                mapper.toDTO(repository.findBySubjectIdAndDeletedFalseOrderByStartTimeDesc(id));
        return subjectTerms.isEmpty() ? subjectTerms : this.mapMissingValues(subjectTerms);
    }

    public Page<SubjectTermDTO> findBySubjectId(Long id, Pageable pageable, String search) {
        if (!subjectRepository.existsById(id)) {
            throw new NotFoundException("Subject not found");
        }

        Page<SubjectTermDTO> subjectTerms =
                repository
                        .findBySubjectIdContaining(id, pageable, "%" + search + "%")
                        .map(mapper::toDTO);
        return subjectTerms.getContent().isEmpty()
                ? subjectTerms
                : new PageImpl<>(
                        this.mapMissingValues(subjectTerms.getContent()),
                        pageable,
                        subjectTerms.getTotalElements());
    }
}
