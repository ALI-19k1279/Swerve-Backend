package com.swerve.backend.subject.service;



import com.swerve.backend.shared.service.BaseService;
import com.swerve.backend.subject.dto.StudentsPerGroup_OfferedCourseDTO;
import com.swerve.backend.subject.mapper.StudentsPerGroup_OfferedCourseMapper;
import com.swerve.backend.subject.model.*;
import com.swerve.backend.subject.repository.StudentsPerGroup_OfferedCourseRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GroupService extends BaseService<StudentsPerGroup_OfferedCourse, StudentsPerGroup_OfferedCourseDTO,Long> {
    private final StudentsPerGroup_OfferedCourseRepository studentsPerGroupOfferedCourseRepository;
    private final StudentsPerGroup_OfferedCourseMapper studentsPerGroupOfferedCourseMapper;
    public GroupService(StudentsPerGroup_OfferedCourseRepository studentsPerGroupOfferedCourseRepository,
                 StudentsPerGroup_OfferedCourseMapper studentsPerGroupOfferedCourseMapper){
        super(studentsPerGroupOfferedCourseRepository,studentsPerGroupOfferedCourseMapper);
        this.studentsPerGroupOfferedCourseRepository=studentsPerGroupOfferedCourseRepository;
        this.studentsPerGroupOfferedCourseMapper=studentsPerGroupOfferedCourseMapper;

    }

    public List<OfferedCourse> GetOfferedCourseByGroupID(Long id){
        List<OfferedCourse> offeredCourses=studentsPerGroupOfferedCourseRepository.findOfferedCoursesByGroupId(id);
        return offeredCourses.isEmpty()? null:offeredCourses;
    }
    public List<OfferedCourse> GetOfferedCourseByStudentID(Long id){
        List<OfferedCourse> offeredCourses=studentsPerGroupOfferedCourseRepository.findOfferedCoursesByStudentId(id);
        return offeredCourses.isEmpty()? null:offeredCourses;
    }

    public Collection<Set<OfferedCourseEvaluationItem>> GetEvaluationItemsByStudentID(Long id) {
        List<OfferedCourseEvaluation> offeredCourseEvaluations = studentsPerGroupOfferedCourseRepository.findEvaluationItemsByStudentId(id);
        Set<Set<OfferedCourseEvaluationItem>> offeredCourseEvaluationItemSet = new HashSet<>();
        for (OfferedCourseEvaluation ev : offeredCourseEvaluations) {
            offeredCourseEvaluationItemSet.add(ev.getOfferedCourseEvaluationItems());
        }

        return offeredCourseEvaluations.isEmpty() ? null : offeredCourseEvaluationItemSet;
    }
}