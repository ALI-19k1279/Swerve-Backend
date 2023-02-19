package com.swerve.backend.subject.service;

import com.swerve.backend.shared.exception.ForbiddenException;
import com.swerve.backend.shared.exception.NotFoundException;
import com.swerve.backend.shared.service.ExtendedService;
import com.swerve.backend.subject.client.FacultyFeignClient;
import com.swerve.backend.subject.dto.SubjectDTO;
import com.swerve.backend.subject.dto.SubjectMaterialDTO;
import com.swerve.backend.subject.dto.TeacherDTO;
import com.swerve.backend.subject.mapper.SubjectMaterialMapper;
import com.swerve.backend.subject.model.Course;
import com.swerve.backend.subject.model.CourseMaterial;
import com.swerve.backend.subject.repository.SubjectMaterialRepository;
import com.swerve.backend.subject.repository.SubjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.swerve.backend.shared.security.SecurityUtils.*;

@Service
public class SubjectMaterialService
        extends ExtendedService<CourseMaterial, SubjectMaterialDTO, Long> {
    private final SubjectMaterialRepository repository;
    private final SubjectMaterialMapper mapper;
    private final SubjectRepository subjectRepository;
    private final FacultyFeignClient facultyFeignClient;

    public SubjectMaterialService(
            SubjectMaterialRepository repository,
            SubjectMaterialMapper mapper,
            SubjectRepository subjectRepository,
            FacultyFeignClient facultyFeignClient) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
        this.subjectRepository = subjectRepository;
        this.facultyFeignClient = facultyFeignClient;
    }

    @Override
    @Transactional
    public SubjectMaterialDTO save(SubjectMaterialDTO subjectMaterialDTO) {
        if (hasAuthority(ROLE_TEACHER)) {
            TeacherDTO teacher = facultyFeignClient.getTeacher(Set.of(getTeacherId())).get(0);
            SubjectDTO subject = subjectMaterialDTO.getSubject();
            if (!subject.getProfessor().getId().equals(teacher.getId())
                    && !subject.getAssistant().getId().equals(teacher.getId())) {
                throw new ForbiddenException(
                        "You are not allowed to manager this subject material");
            }

            if (subjectMaterialDTO.getTeacher() == null) {
                subjectMaterialDTO.setTeacher(teacher);
            }
        }

        return super.save(subjectMaterialDTO);
    }

    @Override
    @Transactional
    public void delete(Set<Long> id) {
        if (hasAuthority(ROLE_TEACHER)) {
            Long teacherId = getTeacherId();
            List<CourseMaterial> courseMaterials =
                    (List<CourseMaterial>) repository.findAllById(id);
            boolean forbidden =
                    courseMaterials.stream()
                            .anyMatch(
                                    subjectMaterial -> {
                                        Course course = subjectMaterial.getCourse();
                                        return !course.getProfessorId().equals(teacherId)
                                                && !course.getAssistantId().equals(teacherId);
                                    });
            if (forbidden) {
                throw new ForbiddenException(
                        "You are not allowed to delete these subject materials");
            }
        }

        super.delete(id);
    }

    @Override
    protected List<SubjectMaterialDTO> mapMissingValues(List<SubjectMaterialDTO> subjectMaterials) {
        map(
                subjectMaterials,
                SubjectMaterialDTO::getTeacher,
                SubjectMaterialDTO::setTeacher,
                facultyFeignClient::getTeacher);

        return subjectMaterials;
    }

    public List<SubjectMaterialDTO> findBySubjectId(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new NotFoundException("Course not found");
        }

        List<SubjectMaterialDTO> subjectMaterials =
                mapper.toDTO(
                        repository.findBySubjectIdAndDeletedFalseOrderByPublicationDateDesc(id));
        return subjectMaterials.isEmpty()
                ? subjectMaterials
                : this.mapMissingValues(subjectMaterials);
    }

    public Page<SubjectMaterialDTO> findBySubjectId(Long id, Pageable pageable, String search) {
        if (!subjectRepository.existsById(id)) {
            throw new NotFoundException("Course not found");
        }

        Page<SubjectMaterialDTO> subjectMaterials =
                repository
                        .findBySubjectIdContaining(id, pageable, "%" + search + "%")
                        .map(mapper::toDTO);
        return subjectMaterials.getContent().isEmpty()
                ? subjectMaterials
                : new PageImpl<>(
                        this.mapMissingValues(subjectMaterials.getContent()),
                        pageable,
                        subjectMaterials.getTotalElements());
    }
}
