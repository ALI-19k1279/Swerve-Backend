package com.swerve.backend.subject.controller;


import com.swerve.backend.shared.controller.BaseController;
import com.swerve.backend.subject.dto.CourseDTO;
import com.swerve.backend.subject.model.Course;
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
    public ResponseEntity<List<CourseDTO>> getCourseByID(@PathVariable Long Id){
        System.out.println("IDDD:::"+Id);
        return new ResponseEntity<>(this.courseService.GetCourseById(Id), HttpStatus.OK);
    }
}
