package com.swerve.backend.subject.controller;


import com.swerve.backend.shared.controller.BaseController;
import com.swerve.backend.subject.dto.CourseDTO;
import com.swerve.backend.subject.dto.LearningTrackDTO;
import com.swerve.backend.subject.model.Course;
import com.swerve.backend.subject.model.LearningTrack;
import com.swerve.backend.subject.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
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
    public ResponseEntity<List<CourseDTO>> getAllCourses(){
        System.out.println("all:::");
        return new ResponseEntity<>(this.courseService.GetAllCourses(), HttpStatus.OK);
    }

    @GetMapping("/{id}/courselt")
    public ResponseEntity<LearningTrack> getCourseLearningTrack(@PathVariable Long id){
        System.out.println("all:::");
        return new ResponseEntity<>(this.courseService.getLearningTrackbyCourseID(id), HttpStatus.OK);
    }

}
