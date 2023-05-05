package com.swerve.backend.subject.service;

import com.swerve.backend.shared.service.BaseService;
import com.swerve.backend.subject.dto.CourseOutlineDTO;
import com.swerve.backend.subject.dto.EvaluationStatsDTO;
import com.swerve.backend.subject.dto.LearnerEvaluationDTO;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public List<LearnerEvaluationDTO> findEvaluationItemsByStudentIdAndOfferedCourseId(Long studentID, Long offeredCourseID){
        List<String> evaluationItemsByStudentIdAndOfferedCourseId = offeredCourseEvaluationRepository.findEvaluationItemsByStudentIdAndOfferedCourseId(studentID, offeredCourseID);
        List<LearnerEvaluationDTO> dtoList = new ArrayList<>();

        for (String result : evaluationItemsByStudentIdAndOfferedCourseId) {
            String[] fields = result.split(",");
            LearnerEvaluationDTO dto = new LearnerEvaluationDTO();
            dto.setTitle(fields[0]);
            dto.setMarksObtained(Integer.parseInt(fields[1]));
            dto.setType(fields[2]);
            dto.setTotalMarks(Integer.parseInt(fields[3]));
            dto.setPublicationDate(fields[4]);
            dto.setDeadlineDate(fields[5]);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public List<EvaluationStatsDTO> findEvaluationItemsMinMaxAverageByTeacherIdAndOfferedCourseId(Long offeredCourseID, Long teacherID){
        List<String> evaluationItemsMinMaxAverageByTeacherIdAndOfferedCourseId = offeredCourseEvaluationRepository.findEvaluationItemsMinMaxAverageByTeacherIdAndOfferedCourseId(offeredCourseID, teacherID);
        List<EvaluationStatsDTO> dtoList = new ArrayList<>();

        for (String result : evaluationItemsMinMaxAverageByTeacherIdAndOfferedCourseId) {
            String[] fields = result.split(",");
            EvaluationStatsDTO dto = new EvaluationStatsDTO();
            dto.setTitle(fields[0]);
            dto.setId(Long.parseLong(fields[1]));
            dto.setMaxMarks(Double.parseDouble(fields[2]));
            dto.setMinMarks(Double.parseDouble(fields[3]));
            dto.setAvgMarks(Double.parseDouble(fields[4]));
            dtoList.add(dto);
        }
        return dtoList;
    }
}
