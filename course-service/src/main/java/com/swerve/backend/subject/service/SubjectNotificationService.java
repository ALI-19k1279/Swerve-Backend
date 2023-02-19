package com.swerve.backend.subject.service;

import com.swerve.backend.shared.exception.ForbiddenException;
import com.swerve.backend.shared.exception.NotFoundException;
import com.swerve.backend.shared.service.ExtendedService;
import com.swerve.backend.subject.client.FacultyFeignClient;
import com.swerve.backend.subject.dto.SubjectDTO;
import com.swerve.backend.subject.dto.SubjectNotificationDTO;
import com.swerve.backend.subject.dto.TeacherDTO;
import com.swerve.backend.subject.mapper.SubjectNotificationMapper;
import com.swerve.backend.subject.model.Course;
import com.swerve.backend.subject.model.SubjectNotification;
import com.swerve.backend.subject.repository.SubjectNotificationRepository;
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
public class SubjectNotificationService
        extends ExtendedService<SubjectNotification, SubjectNotificationDTO, Long> {
    private final SubjectNotificationRepository repository;
    private final SubjectNotificationMapper mapper;
    private final SubjectRepository subjectRepository;
    private final FacultyFeignClient facultyFeignClient;

    public SubjectNotificationService(
            SubjectNotificationRepository repository,
            SubjectNotificationMapper mapper,
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
    public SubjectNotificationDTO save(SubjectNotificationDTO subjectNotificationDTO) {
        if (hasAuthority(ROLE_TEACHER)) {
            TeacherDTO teacher = facultyFeignClient.getTeacher(Set.of(getTeacherId())).get(0);
            SubjectDTO subject = subjectNotificationDTO.getSubject();
            if (!subject.getProfessor().getId().equals(teacher.getId())
                    && !subject.getAssistant().getId().equals(teacher.getId())) {
                throw new ForbiddenException(
                        "You are not allowed to manage this subject notification");
            }

            if (subjectNotificationDTO.getTeacher() == null) {
                subjectNotificationDTO.setTeacher(teacher);
            }
        }

        return super.save(subjectNotificationDTO);
    }

    @Override
    @Transactional
    public void delete(Set<Long> id) {
        if (hasAuthority(ROLE_TEACHER)) {
            Long teacherId = getTeacherId();
            List<SubjectNotification> subjectNotifications =
                    (List<SubjectNotification>) repository.findAllById(id);
            boolean forbidden =
                    subjectNotifications.stream()
                            .anyMatch(
                                    subjectNotification -> {
                                        Course course = subjectNotification.getCourse();
                                        return !course.getProfessorId().equals(teacherId)
                                                && !course.getAssistantId().equals(teacherId);
                                    });
            if (forbidden) {
                throw new ForbiddenException(
                        "You are not allowed to delete these subject notifications");
            }
        }

        super.delete(id);
    }

    @Override
    protected List<SubjectNotificationDTO> mapMissingValues(
            List<SubjectNotificationDTO> subjectNotifications) {
        map(
                subjectNotifications,
                SubjectNotificationDTO::getTeacher,
                SubjectNotificationDTO::setTeacher,
                facultyFeignClient::getTeacher);

        return subjectNotifications;
    }

    public List<SubjectNotificationDTO> findBySubjectId(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new NotFoundException("Course not found");
        }

        List<SubjectNotificationDTO> subjectNotifications =
                mapper.toDTO(
                        repository.findBySubjectIdAndDeletedFalseOrderByPublicationDateDesc(id));
        return subjectNotifications.isEmpty()
                ? subjectNotifications
                : this.mapMissingValues(subjectNotifications);
    }

    public Page<SubjectNotificationDTO> findBySubjectId(Long id, Pageable pageable, String search) {
        if (!subjectRepository.existsById(id)) {
            throw new NotFoundException("Course not found");
        }

        Page<SubjectNotificationDTO> subjectNotifications =
                repository
                        .findBySubjectIdContaining(id, pageable, "%" + search + "%")
                        .map(mapper::toDTO);
        return subjectNotifications.getContent().isEmpty()
                ? subjectNotifications
                : new PageImpl<>(
                        this.mapMissingValues(subjectNotifications.getContent()),
                        pageable,
                        subjectNotifications.getTotalElements());
    }
}
