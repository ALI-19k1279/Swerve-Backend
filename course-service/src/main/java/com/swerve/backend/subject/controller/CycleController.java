package com.swerve.backend.subject.controller;

import com.swerve.backend.shared.controller.BaseController;
import com.swerve.backend.subject.dto.CourseDTO;
import com.swerve.backend.subject.dto.CycleDTO;
import com.swerve.backend.subject.model.Course;
import com.swerve.backend.subject.model.Cycle;
import com.swerve.backend.subject.service.CourseService;
import com.swerve.backend.subject.service.CycleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping("/cycles")
public class CycleController extends BaseController<Cycle, CycleDTO,Long>{

    private final CycleService cycleService;

    public CycleController(CycleService cycleService){
        super(cycleService);
        this.cycleService=cycleService;
    }

    @GetMapping("/{id}/cycle")
    public ResponseEntity<List<CycleDTO>> getCycleById(@PathVariable long id){
        return new ResponseEntity<>(cycleService.GetCycleById(id),HttpStatus.OK);
    }
}
