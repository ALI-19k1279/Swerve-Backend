package com.swerve.backend.subject.controller;

import com.swerve.backend.shared.controller.BaseController;
import com.swerve.backend.shared.service.BaseService;
import com.swerve.backend.subject.dto.AttendanceDTO;
import com.swerve.backend.subject.dto.GroupDTO;
import com.swerve.backend.subject.model.OfferedCourseAttendance;
import com.swerve.backend.subject.service.AttendanceService;
import com.swerve.backend.subject.service.GroupService;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/attendance")
public class AttendanceController extends BaseController<OfferedCourseAttendance, AttendanceDTO,Long> {

    private final AttendanceService attendanceService;
    public AttendanceController(AttendanceService attendanceService) {
        super(attendanceService);
        this.attendanceService=attendanceService;
    }

    @GetMapping("/{ocid}/{stdid}/bycourse")
    public ResponseEntity<List<AttendanceDTO>> getLearnerOfferedCourseAttendance(
                                                                               @PathVariable Long ocid,
                                                                               @PathVariable Long stdid){

        return new ResponseEntity<>(this.attendanceService.getLearnerOfferedCourseAttendance(
                ocid,stdid), HttpStatus.OK);

    }
    @GetMapping("/{gid}/{ocid}")
    public ResponseEntity<List<AttendanceDTO>> getGroupOfferedCourseAttendance(@PathVariable Long gid,
                                                                                      @PathVariable Long ocid
                                                                                      ){
        return new ResponseEntity<>(this.attendanceService.getOfferedCourseGroupAttendance(gid,
                ocid), HttpStatus.OK);

    }

    @GetMapping("/{gid}/{ocid}/{date}/bydate")
    public ResponseEntity<List<AttendanceDTO>> getGroupOfferedCourseAttendanceByDate(@PathVariable Long gid,
                                                                               @PathVariable Long ocid,
                                                                                     @PathVariable @DateTimeFormat(pattern = "yyyy-M-d") Date date
    ){
        return new ResponseEntity<>(this.attendanceService.getOfferedCourseGroupAttendanceByDate(gid,
                ocid,date), HttpStatus.OK);


    }
    @GetMapping("/{gid}/{ocid}/{date}/poppulate")
    public ResponseEntity<List<AttendanceDTO>> poppulateInstructorsAttendanceGrid(@PathVariable Long gid,
                                                                                     @PathVariable Long ocid,
                                                                                     @PathVariable @DateTimeFormat(pattern = "yyyy-M-d") Date date
    ){
        return new ResponseEntity<>(this.attendanceService.populateInstructorAttendanceTable(gid,
                ocid,date), HttpStatus.OK);

    }

    @PostMapping("/mark")
    public ResponseEntity<?> markAttendance(@RequestBody List<AttendanceDTO> attendanceList){
        if(this.attendanceService.markAttendance(attendanceList))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to mark attendance");
    }
    @PatchMapping("/update")
    public ResponseEntity<?>  updateAttendance(@RequestBody List<AttendanceDTO> attendanceList){
        if(this.attendanceService.updateAttendance(attendanceList))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update attendance");
    }
    @PostMapping("/process")
    public ResponseEntity<String> processAttendance(@RequestBody List<AttendanceDTO> attendanceList) {
        boolean result = attendanceService.processAttendance(attendanceList);
        if (result) {
            return ResponseEntity.ok("Attendance processed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process attendance");
        }
    }
    @GetMapping("/{gid}/{stid}/all")
    public ResponseEntity<List<AttendanceDTO>> getOfferedCourseAttendances(@PathVariable Long gid,
                                                                               @PathVariable Long stid
    ){
        return new ResponseEntity<>(this.attendanceService.getOfferedCourseAttendances(gid,
                stid), HttpStatus.OK);

    }
}
