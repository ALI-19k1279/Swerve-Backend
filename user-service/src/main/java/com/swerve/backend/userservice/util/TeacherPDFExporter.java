package com.swerve.backend.userservice.util;

import com.swerve.backend.userservice.dto.TeacherDTO;
import com.swerve.backend.shared.util.PDFExporter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TeacherPDFExporter extends PDFExporter<TeacherDTO, Long> {
    public TeacherPDFExporter() {
        super(
                "Teacher list",
                new String[] {
                    "ID", "Username", "First name", "Last name",
                },
                new float[] {1f, 4f, 3f, 3f},
                new ArrayList<>(
                        List.of(
                                teacher -> String.valueOf(teacher.getId()),
                                teacher -> teacher.getUser().getUsername(),
                                TeacherDTO::getFirstName,
                                TeacherDTO::getLastName)));
    }
}
