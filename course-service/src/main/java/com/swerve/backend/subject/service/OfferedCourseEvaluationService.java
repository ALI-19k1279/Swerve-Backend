package com.swerve.backend.subject.service;

import com.swerve.backend.shared.service.BaseService;
import com.swerve.backend.subject.dto.CourseOutlineDTO;
import com.swerve.backend.subject.dto.OfferedCourseEvaluationDTO;
import com.swerve.backend.subject.mapper.CourseOutlineMapper;
import com.swerve.backend.subject.mapper.OfferedCourseEvaluationMapper;
import com.swerve.backend.subject.model.OfferedCourseEvaluation;
import com.swerve.backend.subject.model.OfferedCourseOutline;
import com.swerve.backend.subject.repository.OfferedCourseEvaluationRepository;
import com.swerve.backend.subject.repository.OfferedCourseOutlineRepository;
import com.swerve.backend.subject.repository.OfferedCourseRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OfferedCourseEvaluationService extends BaseService<OfferedCourseEvaluation, OfferedCourseEvaluationDTO,Long> {

    private final OfferedCourseEvaluationRepository offeredCourseEvaluationRepository;
    private final OfferedCourseEvaluationMapper offeredCourseEvaluationMapper;

    public OfferedCourseEvaluationService(OfferedCourseEvaluationRepository offeredCourseEvaluationRepository, OfferedCourseEvaluationMapper offeredCourseEvaluationMapper) {
        super(offeredCourseEvaluationRepository,offeredCourseEvaluationMapper);
        this.offeredCourseEvaluationMapper=offeredCourseEvaluationMapper;
        this.offeredCourseEvaluationRepository=offeredCourseEvaluationRepository;
    }

    public List<String> findEvaluationItemsByStudentIdAndOfferedCourseId(Long studentID, Long offeredCourseID){
        List<String> evaluationItemsByStudentIdAndOfferedCourseId = offeredCourseEvaluationRepository.findEvaluationItemsByStudentIdAndOfferedCourseId(studentID, offeredCourseID);
        return evaluationItemsByStudentIdAndOfferedCourseId;
    }

    public List<String> findEvaluationItemsMinMaxAverageByTeacherIdAndOfferedCourseId(Long offeredCourseID, Long teacherID){
        List<String> evaluationItemsMinMaxAverageByTeacherIdAndOfferedCourseId = offeredCourseEvaluationRepository.findEvaluationItemsMinMaxAverageByTeacherIdAndOfferedCourseId(offeredCourseID, teacherID);
        return evaluationItemsMinMaxAverageByTeacherIdAndOfferedCourseId;
    }
}
