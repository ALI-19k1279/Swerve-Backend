package com.swerve.backend.subject.controller;

import com.swerve.backend.shared.controller.BaseController;
import com.swerve.backend.subject.dto.CourseOutlineDTO;
import com.swerve.backend.subject.model.OfferedCourseOutline;
import com.swerve.backend.subject.service.CourseOutlineService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courseoutline")
public class CourseOutlineController extends BaseController<OfferedCourseOutline, CourseOutlineDTO,Long> {

    private final CourseOutlineService courseOutlineService;

    public CourseOutlineController(CourseOutlineService courseOutlineService) {
        super(courseOutlineService);
        this.courseOutlineService = courseOutlineService;
    }
    @PreAuthorize("hasAnyAuthority('viewCourse')")
    @GetMapping("/{offeredCourseId}/offeredcourse")
    public ResponseEntity<CourseOutlineDTO> getCourseOutlineByOfferedCourseId(@PathVariable Long offeredCourseId) {
        CourseOutlineDTO courseOutlineDTOs = courseOutlineService.getCourseOutlineByOfferedCourseId(offeredCourseId);
        return ResponseEntity.ok(courseOutlineDTOs);
    }

    @PreAuthorize("hasAnyAuthority('updateCourse')")
    @PostMapping("/{offeredCourseId}/{teacherId}/update/outline")
    public ResponseEntity<?> getCourseOutlineByOfferedCourseId(@PathVariable Long offeredCourseId,@PathVariable Long teacherId,
                                                                              @RequestBody CourseOutlineDTO courseOutlineDTO) {
        CourseOutlineDTO courseOutlineDTOs = courseOutlineService.saveOrUpdateCourseOutline(offeredCourseId,teacherId,courseOutlineDTO);
        return ResponseEntity.ok(courseOutlineDTOs);
    }
}
