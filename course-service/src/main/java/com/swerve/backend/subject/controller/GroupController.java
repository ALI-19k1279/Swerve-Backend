package com.swerve.backend.subject.controller;



import com.swerve.backend.shared.controller.BaseController;
import com.swerve.backend.subject.dto.*;
import com.swerve.backend.subject.model.*;
import com.swerve.backend.subject.service.CourseOutlineService;
import com.swerve.backend.subject.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/spgoc")
public class GroupController extends BaseController<StudentsPerGroup_OfferedCourse, StudentsPerGroup_OfferedCourseDTO,Long> {
    private final GroupService groupService;


    public GroupController(GroupService groupService, CourseOutlineService courseOutlineService){
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


    @GetMapping("/{stdid}/{ocid}/evaluationitems")
    public ResponseEntity< Map<String, List<OfferedCourseEvaluation>> > getEvaluationItemsByStudentID(@PathVariable  Long stdid,
                                                                                                       @PathVariable Long ocid){
        return new ResponseEntity<>(groupService.getEvaluationItemsByStudentId(stdid,ocid),HttpStatus.OK);
    }
    @GetMapping("/{tid}/offeredcourses/tid")
    public ResponseEntity<List<OfferedCourse>> findOfferedCourseByTeacherID(@PathVariable  Long tid){
        return new ResponseEntity<>(groupService.GetOfferedCourseByTeacherId(tid),HttpStatus.OK);
    }

    @GetMapping("/{ocid}/groups/ocid")
    public ResponseEntity<List<Group>> findSPGOCByOfferedCourseId(@PathVariable  Long ocid){
        return new ResponseEntity<>(groupService.GetSPGOCByOfferedCourseId(ocid),HttpStatus.OK);
    }


//    @GetMapping("/{gid}/{ocid}/evaluations/stdid")
//    public ResponseEntity<LearnerEvaluationDTO> getEvaluationsByGroupID(@PathVariable Long gid,@PathVariable Long ocid){
//        return new null;
//    }

//    @GetMapping("/{id}/students")
//    public ResponseEntity<List<Integer>> getStudentsDetails(){
//            return new ResponseEntity<>(groupService.getStudentsDetail(),HttpStatus.OK);
//    }
}
