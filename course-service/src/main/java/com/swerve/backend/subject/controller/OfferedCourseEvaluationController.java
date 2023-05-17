package com.swerve.backend.subject.controller;

import com.swerve.backend.shared.controller.BaseController;
import com.swerve.backend.subject.dto.*;
import com.swerve.backend.subject.model.OfferedCourseEvaluation;
import com.swerve.backend.subject.service.OfferedCourseEvaluationService;
import org.apache.poi.xwpf.usermodel.*;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;


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
    @PostMapping("/create-coursework")
    public ResponseEntity<?> createEvalItem( @RequestParam("title") String title,
                                             @RequestParam("type") String type,
                                             @RequestParam("canReattempt") String canReattempt,
                                             @RequestParam("totalMarks") int totalMarks,
                                             @RequestParam("passingMarks") int passingMarks,
                                             @RequestParam("publicationDate") String publicationDate,
                                             @RequestParam("deadline") String deadlineDate,
                                             @RequestParam("groupId") String groupId,
                                             @RequestParam("offeredCourseId") String offeredCourseId,
                                             @RequestParam("teacher") String teacherId,
                                             @RequestParam("file") MultipartFile file,
                                             @RequestParam("instructions") String instructions){
        try {
            boolean canReattemptBool= canReattempt.equals("Yes");
            System.out.println("pubDate");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime pubDate = LocalDateTime.parse(publicationDate, formatter);
            System.out.println("pubDate2222");
            LocalDateTime dedDate = LocalDateTime.parse(deadlineDate, formatter);
            System.out.println(pubDate);
            offeredCourseEvaluationService.createOfferedCourseEvaluationItem(title,
            type,
            canReattemptBool,
          totalMarks,
            passingMarks,
                    pubDate,
                    dedDate,
            Long.parseLong(groupId),
            Long.parseLong(offeredCourseId),
           Long.parseLong(teacherId),
                    file,
           instructions);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (DateTimeParseException e) {
            String errorMessage = "Invalid date format. Please provide the date in the format 'yyyy-MM-dd'.";
            System.out.println(errorMessage);
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Log the exception using a logging framework
            System.out.println("An error occurred while creating the evaluation item. "+ e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/update-coursework")
    public ResponseEntity<?> updateEvalItemDeadlineDate(
                                             @RequestParam("deadline") String deadlineDate,
                                             @RequestParam("evalItemId") String evalItemId
                                             ){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dedDate = LocalDateTime.parse(deadlineDate, formatter);
            offeredCourseEvaluationService.updateOfferedCourseEvaluationItemDeadline(
                    Long.parseLong(evalItemId),
                    dedDate
                    );
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (DateTimeParseException e) {
            String errorMessage = "Invalid date format. Please provide the date in the format 'yyyy-MM-dd'.";
            System.out.println(errorMessage);
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("An error occurred while updating the evaluation item. "+ e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{itemId}/delete-coursework-item")
    public ResponseEntity<?> deleteEvalItem(@PathVariable Long itemId){
        try{
            Boolean result=offeredCourseEvaluationService.deleteEvalItem(itemId);
            if(result)
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            System.out.println("An error occurred while deleting the evaluation item. "+ e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{ocid}/{gid}/coursework-submissions")
    public ResponseEntity<List<OfferedCourseEvaluation>> viewSubmissions(@PathVariable Long ocid,@PathVariable Long gid){
        try{
            return new ResponseEntity<>(this.offeredCourseEvaluationService.getSubmissionsByGroupIdandOfferedCourseID(ocid,gid),HttpStatus.OK);
        }
        catch (Exception e){
            System.out.println("An error occurred while getting the coursework. "+ e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    @PatchMapping("/{ocid}/{stdid}/grade-student-submission")
//    public ResponseEntity<HttpStatus> gradeSubmissions(@PathVariable Long ocid,@PathVariable Long stdid,
//                                                       @RequestParam("marks" ) String marks){
//        try{
//            Boolean result=offeredCourseEvaluationService.gradeSubmissions(ocid, stdid,Integer.parseInt(marks));
//            if(result)
//                return new ResponseEntity<>(HttpStatus.OK);
//            else
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        catch (Exception e){
//            System.out.println("An error occurred while deleting the evaluation item. "+ e);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PatchMapping("/{ocid}/grade-coursework-submissions")
    public ResponseEntity<HttpStatus> gradeSubmissions(@PathVariable Long ocid,
                                                       @RequestBody  List<LearnerMarksDTO> studentMarks
                                                       ) {
        try {
            offeredCourseEvaluationService.bulkGradeSubmissions(ocid, studentMarks);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("An error occurred while grading the coursework submissions. " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/{ocid}/submit-coursework")
    public ResponseEntity<HttpStatus> evalItemSubmission(@PathVariable Long ocid,
                                                       @RequestParam("studentId") Long studentID,
                                                         @RequestParam("file") MultipartFile file,
                                                         @RequestParam("evalItemId") Long evalItemId,
                                                         @RequestParam("attempts") Long attempts
    ) {
        try {
            offeredCourseEvaluationService.evalItemSubmission(ocid,studentID,
                    file,
                    evalItemId,
                    attempts);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("An error occurred while submitting coursework. " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
