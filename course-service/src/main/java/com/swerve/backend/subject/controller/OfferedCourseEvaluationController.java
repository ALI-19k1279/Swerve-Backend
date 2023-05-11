package com.swerve.backend.subject.controller;

import com.swerve.backend.shared.controller.BaseController;
import com.swerve.backend.subject.dto.EvaluationStatsDTO;
import com.swerve.backend.subject.dto.LearnerEvaluationDTO;
import com.swerve.backend.subject.dto.OfferedCourseEvaluationDTO;
import com.swerve.backend.subject.model.OfferedCourseEvaluation;
import com.swerve.backend.subject.service.OfferedCourseEvaluationService;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static com.swerve.backend.subject.util.Utility.generateDoc;

@Controller
@RequestMapping("/evaluation")
public class OfferedCourseEvaluationController extends BaseController<OfferedCourseEvaluation, OfferedCourseEvaluationDTO,Long> {
    private final OfferedCourseEvaluationService offeredCourseEvaluationService;

    public OfferedCourseEvaluationController(OfferedCourseEvaluationService offeredCourseEvaluationService) {
        super(offeredCourseEvaluationService);
        this.offeredCourseEvaluationService = offeredCourseEvaluationService;
    }

    @GetMapping("/{ocid}/{stdid}/bycourse")
    public ResponseEntity<List<LearnerEvaluationDTO>> getEvaluationItemsByStudentIdAndOfferedCourseId(
            @PathVariable Long ocid,
            @PathVariable Long stdid){

        return new ResponseEntity<>(this.offeredCourseEvaluationService.findEvaluationItemsByStudentIdAndOfferedCourseId(stdid,ocid), HttpStatus.OK);

    }
    @GetMapping("/{ocid}/minmaxavgbycourse")
    public ResponseEntity<List<EvaluationStatsDTO>> getEvaluationItemsMinMaxAverageByTeacherIdAndOfferedCourseId(
            @PathVariable Long ocid){

        return new ResponseEntity<>(this.offeredCourseEvaluationService.findEvaluationItemsMinMaxAverageByTeacherIdAndOfferedCourseId(ocid), HttpStatus.OK);

    }

    @PostMapping("/convert-to-doc")
    public ResponseEntity<byte[]> convertToDoc(@RequestBody String content) {
        // Generate the document using Apache POI
        byte[] docBytes = generateDoc(content);

        // Set the appropriate headers for the response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("output.doc").build());

        // Return the document as a downloadable file
        return new ResponseEntity<>(docBytes, headers, HttpStatus.OK);
    }
}
