package com.swerve.backend.subject.service;



import com.fasterxml.jackson.databind.util.JSONPObject;
import com.swerve.backend.shared.service.BaseService;
import com.swerve.backend.subject.dto.LearnerEvaluationDTO;
import com.swerve.backend.subject.dto.StudentsPerGroup_OfferedCourseDTO;
import com.swerve.backend.subject.mapper.StudentsPerGroup_OfferedCourseMapper;
import com.swerve.backend.subject.model.*;
import com.swerve.backend.subject.repository.LearningTrackRepository;
import com.swerve.backend.subject.repository.StudentsPerGroup_OfferedCourseRepository;
import org.springframework.stereotype.Service;

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

    public List<String> GetEvaluationItemsByStudentID(Long stdid,Long gid,Long ocid) {
        List<String> learnerEvaluationDTOS = studentsPerGroupOfferedCourseRepository.findEvaluationItemsByStudentId(stdid,gid,ocid);
        List<String> maxMinAvg=studentsPerGroupOfferedCourseRepository.findMaxMinAvgbyOfferedCourseID(ocid);
        //        List<LearnerEvaluationDTO> evaluationDTOS=new ArrayList<LearnerEvaluationDTO>();
//        int size=evaluationDTOS.size();
//        for(int i=0;i<size;i++){
//            evalua
//        }
//        System.out.println(maxMinAvg.get(0));
        return learnerEvaluationDTOS.isEmpty()? null:learnerEvaluationDTOS;
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
