package com.swerve.backend.subject.controller;

import com.swerve.backend.shared.controller.BaseController;
import com.swerve.backend.subject.dto.AttendanceDTO;
import com.swerve.backend.subject.dto.CourseOutlineDTO;
import com.swerve.backend.subject.dto.OfferedCourseEvaluationDTO;
import com.swerve.backend.subject.model.OfferedCourseEvaluation;
import com.swerve.backend.subject.model.OfferedCourseOutline;
import com.swerve.backend.subject.service.CourseOutlineService;
import com.swerve.backend.subject.service.OfferedCourseEvaluationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/evaluation")
public class OfferedCourseEvaluationController extends BaseController<OfferedCourseEvaluation, OfferedCourseEvaluationDTO,Long> {
    private final OfferedCourseEvaluationService offeredCourseEvaluationService;

    public OfferedCourseEvaluationController(OfferedCourseEvaluationService offeredCourseEvaluationService) {
        super(offeredCourseEvaluationService);
        this.offeredCourseEvaluationService = offeredCourseEvaluationService;
    }

    @GetMapping("/{ocid}/{stdid}/bycourse")
    public ResponseEntity<List<String>> getEvaluationItemsByStudentIdAndOfferedCourseId(
            @PathVariable Long ocid,
            @PathVariable Long stdid){

        return new ResponseEntity<>(this.offeredCourseEvaluationService.findEvaluationItemsByStudentIdAndOfferedCourseId(stdid,ocid), HttpStatus.OK);

    }
    @GetMapping("/{ocid}/{teacherid}/minmaxavgbycourse")
    public ResponseEntity<List<String>> getEvaluationItemsMinMaxAverageByTeacherIdAndOfferedCourseId(
            @PathVariable Long ocid,
            @PathVariable Long teacherid){

        return new ResponseEntity<>(this.offeredCourseEvaluationService.findEvaluationItemsMinMaxAverageByTeacherIdAndOfferedCourseId(ocid,teacherid), HttpStatus.OK);

    }
}
