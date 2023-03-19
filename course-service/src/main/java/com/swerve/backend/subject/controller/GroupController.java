package com.swerve.backend.subject.controller;



import com.swerve.backend.shared.controller.BaseController;
import com.swerve.backend.subject.dto.CourseDTO;
import com.swerve.backend.subject.dto.LearningTrackDTO;
import com.swerve.backend.subject.dto.StudentsPerGroup_OfferedCourseDTO;
import com.swerve.backend.subject.model.*;
import com.swerve.backend.subject.service.CourseService;
import com.swerve.backend.subject.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.swerve.backend.subject.dto.LearnerEvaluationDTO;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/spgoc")
public class GroupController extends BaseController<StudentsPerGroup_OfferedCourse, StudentsPerGroup_OfferedCourseDTO,Long> {
    private final GroupService groupService;

    public GroupController(GroupService groupService){
        super(groupService);
        this.groupService=groupService;
    }

    @GetMapping("/{id}/offeredcourses/gid")
    public ResponseEntity<List<OfferedCourse>> getOfferedCourseByGroupID(@PathVariable Long id){
        return new ResponseEntity<>(groupService.GetOfferedCourseByGroupID(id),HttpStatus.OK);
    }

    @GetMapping("/{id}/offeredcourses/stdid")
    public ResponseEntity<List<OfferedCourse>> getOfferedCourseByStudentID(@PathVariable Long id){
        return new ResponseEntity<>(groupService.GetOfferedCourseByStudentID(id),HttpStatus.OK);
    }

//    @GetMapping("/{id}/evaluationitems/stdid")
//    public ResponseEntity<Collection<Set<OfferedCourseEvaluationItem>>> getEvaluationItemsByStudentID(@PathVariable Long id){
//        return new ResponseEntity<>(groupService.GetEvaluationItemsByStudentID(id),HttpStatus.OK);
//    }
//    @GetMapping("/{stdid}/{crcid}/evaluations/stdid")
//    public ResponseEntity<LearnerEvaluationDTO> getEvaluationsByStudentID(@PathVariable Long stdid,@PathVariable Long crcid){
//        return new ResponseEntity<>(groupService.getEvaluationsByStudentIDandOfferedCourseID(stdid,crcid),HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}/students")
//    public ResponseEntity<List<Integer>> getStudentsDetails(){
//            return new ResponseEntity<>(groupService.getStudentsDetail(),HttpStatus.OK);
//    }
}
