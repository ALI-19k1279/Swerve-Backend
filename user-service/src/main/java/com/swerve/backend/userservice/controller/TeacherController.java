package com.swerve.backend.userservice.controller;

import com.swerve.backend.userservice.dto.TeacherDTO;
import com.swerve.backend.userservice.model.Teacher;
import com.swerve.backend.userservice.service.TeacherService;
import com.swerve.backend.userservice.util.TeacherPDFExporter;
import com.swerve.backend.shared.controller.BaseController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController extends BaseController<Teacher, TeacherDTO, Long> {
    private final TeacherService service;
    private final TeacherPDFExporter pdfExporter;

    public TeacherController(TeacherService service, TeacherPDFExporter pdfExporter) {
        super(service);
        this.service = service;
        this.pdfExporter = pdfExporter;
    }

    @GetMapping(value = "/all/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public void getAllPdf(HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=teachers.pdf");
        List<TeacherDTO> teachers = service.findAll();
        pdfExporter.export(teachers, response);
    }

    @GetMapping("/user-id/{id}/id")
    public ResponseEntity<Long> getIdByUserId(@PathVariable Long id) {
        return new ResponseEntity<>(this.service.findByUserId(id).getId(), HttpStatus.OK);
    }
}
