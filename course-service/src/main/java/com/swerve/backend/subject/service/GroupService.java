package com.swerve.backend.subject.service;



import com.fasterxml.jackson.databind.util.JSONPObject;
import com.swerve.backend.shared.service.BaseService;
import com.swerve.backend.subject.dto.LearnerEvaluationDTO;
import com.swerve.backend.subject.dto.OfferedCourseEvaluationDTO;
import com.swerve.backend.subject.dto.OfferedCourseEvaluationItemDTO;
import com.swerve.backend.subject.dto.StudentsPerGroup_OfferedCourseDTO;
import com.swerve.backend.subject.mapper.StudentsPerGroup_OfferedCourseMapper;
import com.swerve.backend.subject.model.*;
import com.swerve.backend.subject.repository.LearningTrackRepository;
import com.swerve.backend.subject.repository.StudentsPerGroup_OfferedCourseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class GroupService extends BaseService<StudentsPerGroup_OfferedCourse, StudentsPerGroup_OfferedCourseDTO,Long> {
    private final StudentsPerGroup_OfferedCourseRepository studentsPerGroupOfferedCourseRepository;
    private final StudentsPerGroup_OfferedCourseMapper studentsPerGroupOfferedCourseMapper;
    private final LearningTrackRepository learningTrackRepository;

    public GroupService(StudentsPerGroup_OfferedCourseRepository studentsPerGroupOfferedCourseRepository,
                 StudentsPerGroup_OfferedCourseMapper studentsPerGroupOfferedCourseMapper,
                        LearningTrackRepository learningTrackRepository){
        super(studentsPerGroupOfferedCourseRepository,studentsPerGroupOfferedCourseMapper);
        this.studentsPerGroupOfferedCourseRepository=studentsPerGroupOfferedCourseRepository;
        this.studentsPerGroupOfferedCourseMapper=studentsPerGroupOfferedCourseMapper;

        this.learningTrackRepository = learningTrackRepository;
    }

    public List<OfferedCourse> GetOfferedCourseByGroupID(Long id){
        List<OfferedCourse> offeredCourses=studentsPerGroupOfferedCourseRepository.findOfferedCoursesByGroupId(id);
        return offeredCourses.isEmpty()? null:offeredCourses;
    }
    public List<OfferedCourse> GetOfferedCourseByStudentID(Long id){
        List<OfferedCourse> offeredCourses=studentsPerGroupOfferedCourseRepository.findOfferedCoursesByStudentId(id);
        return offeredCourses.isEmpty()? null:offeredCourses;
    }
    public List<OfferedCourse> GetOfferedCourseByTeacherId(Long id){
        List<OfferedCourse> offeredCourses=studentsPerGroupOfferedCourseRepository.findOfferedCoursesByTeacherId(id);
        return offeredCourses.isEmpty()? null:offeredCourses;
    }

    public List<Group> GetSPGOCByOfferedCourseId(Long id){
        List<Group> byOfferedCourseIdAndDeletedFalse = studentsPerGroupOfferedCourseRepository.findByOfferedCourseIdAndDeletedFalse(id);
        return byOfferedCourseIdAndDeletedFalse.isEmpty()? null:byOfferedCourseIdAndDeletedFalse;
    }

    public Map<String, List<OfferedCourseEvaluation>> getEvaluationItemsByStudentId(Long studentId, Long offeredCourseId) {
        Map<String, List<OfferedCourseEvaluation>> evaluationMap = new HashMap<>();

        Object[] result = studentsPerGroupOfferedCourseRepository.findEvaluationItemsByStudentId(studentId, offeredCourseId);

        for (Object obj : result) {
            Object[] arr = (Object[]) obj;
            String type = (String) arr[0];
            OfferedCourseEvaluation oce = (OfferedCourseEvaluation) arr[1];

            if (evaluationMap.containsKey(type)) {
                evaluationMap.get(type).add(oce);
            } else {
                List<OfferedCourseEvaluation> list = new ArrayList<>();
                list.add(oce);
                evaluationMap.put(type, list);
            }
        }

        return evaluationMap;
    }
    public List<OfferedCourseEvaluationItem>  getEvaluationItemsByTeacherId(Long teacherId, Long offeredCourseId) {


        List<OfferedCourseEvaluationItem> result = studentsPerGroupOfferedCourseRepository.findEvaluationItemsByTeacherId(teacherId, offeredCourseId);

        return result.isEmpty()?null:result;
    }

    public List<StudentsPerGroup_OfferedCourse> GetSPGOCByGroupIdAndOfferedCourseId(Long groupId, Long offeredCourseId){
        List<StudentsPerGroup_OfferedCourse> byGroupIdAndOfferedCourseIdAndDeletedFalse = studentsPerGroupOfferedCourseRepository.findByGroupIdAndOfferedCourseIdAndDeletedFalse(groupId, offeredCourseId);
        return byGroupIdAndOfferedCourseIdAndDeletedFalse;
    }


//
//    public LearnerEvaluationDTO getEvaluationsByStudentIDandOfferedCourseID(Long stdID,Long offeredCourseID) {
//        List<OfferedCourseEvaluation> offeredCourseEvaluations = studentsPerGroupOfferedCourseRepository.findEvaluationItemsByStudentIdandOfferedCourseID(stdID,offeredCourseID);
//
//        LearnerEvaluationDTO learnerEvaluationDTO=new LearnerEvaluationDTO();
//        learnerEvaluationDTO.setLearnerID(stdID);
//        learnerEvaluationDTO.setMarksObtained(offeredCourseEvaluations.get(0).getMarksObtained());
//
//        return offeredCourseEvaluations.isEmpty() ? null : learnerEvaluationDTO;
//    }
//    public List<Integer> getStudentsDetail(){
//        List<Integer> marks=studentsPerGroupOfferedCourseRepository.findStudentsByGroupID();
//        return marks.isEmpty()? null:marks;
//    }
}
