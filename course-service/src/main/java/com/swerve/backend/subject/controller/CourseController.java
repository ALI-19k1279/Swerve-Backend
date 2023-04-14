package com.swerve.backend.subject.controller;


import com.swerve.backend.shared.controller.BaseController;
import com.swerve.backend.subject.dto.CourseDTO;
import com.swerve.backend.subject.dto.LearningTrackDTO;
import com.swerve.backend.subject.model.Course;
import com.swerve.backend.subject.model.LearningTrack;
import com.swerve.backend.subject.model.OfferedCourse;
import com.swerve.backend.subject.model.PreRequisite;
import com.swerve.backend.subject.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.Collection;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
@RequestMapping("/courses")
public class CourseController extends BaseController<Course, CourseDTO,Long> {

    private final CourseService courseService;

    public CourseController(CourseService courseService){
        super(courseService);
        this.courseService=courseService;
    }

    @GetMapping("/{id}/course")
    public ResponseEntity<CourseDTO> getCourseByID(@PathVariable Long id){
        System.out.println("IDDD:::"+this.courseService.GetCourseById(id));
        return new ResponseEntity<>(this.courseService.GetCourseById(id), HttpStatus.OK);
    }
    @GetMapping("/all/course")
    @PreAuthorize("hasAnyAuthority('auth:hello','auth:read')")
    public ResponseEntity<List<CourseDTO>> getAllCourses(){
        System.out.println("all:::");
        return new ResponseEntity<>(this.courseService.GetAllCourses(), HttpStatus.OK);
    }

    @GetMapping("/{id}/learningtrack")
    public ResponseEntity<LearningTrack> getCourseLearningTrack(@PathVariable Long id){
        System.out.println("all:::");
        return new ResponseEntity<>(this.courseService.getLearningTrackbyCourseID(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/prereq")
    public ResponseEntity<List<PreRequisite>> getCoursePreRequisite(@PathVariable Long id){
        System.out.println("all:::");
        return new ResponseEntity<>(this.courseService.getPreRequisitebyCourseID(id), HttpStatus.OK);
    }
    @GetMapping("/offeredcourses")
    public ResponseEntity<List<OfferedCourse>> getAllOfferedCourses(){
        System.out.println("all:::");
        return new ResponseEntity<>(this.courseService.GetAllOfferedCourses(), HttpStatus.OK);
    }
    @RequestMapping(path = "/create", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<CourseDTO> create(@RequestParam String course_code,
                                         @RequestParam String credits,
                                         @RequestParam String short_description,
                                         @RequestParam String title,
                                         @RequestParam String learning_track_id) {
        System.out.println(course_code);
        System.out.println(credits);
        System.out.println(short_description);
        System.out.println(title);
        System.out.println(learning_track_id);
        CourseDTO courseDTO= new CourseDTO(course_code,credits,
                short_description,title,learning_track_id);

//        CourseDTO course = courseService.save(courseDTO);
        courseService.CreateCourse(course_code,credits,short_description
                ,title,Long.parseLong(learning_track_id));
//        if (course == null) {
//            throw new ServerException;
//        } else {
//            return new ResponseEntity<>(course, HttpStatus.CREATED);
//        }
        return new ResponseEntity<>(courseDTO, HttpStatus.CREATED);
    }

}
