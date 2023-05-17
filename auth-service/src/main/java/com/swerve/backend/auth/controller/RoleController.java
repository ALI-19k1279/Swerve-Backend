package com.swerve.backend.auth.controller;

import com.swerve.backend.auth.model.Role;
import com.swerve.backend.auth.service.RoleService;
import com.swerve.backend.shared.controller.BaseController;
import com.swerve.backend.shared.dto.RoleDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/roles")
public class RoleController extends BaseController<Role, RoleDTO, Long> {
    private final RoleService service;

    public RoleController(RoleService service) {
        super(service);
        this.service = service;
    }

    @PostMapping("/createRole")
    public ResponseEntity<?> create(@RequestParam("role") String role) {
        // Handle file and other parameters here
        service.createRole(role);

        return new ResponseEntity<>( HttpStatus.CREATED);
    }
    @GetMapping("/role/{role}")
    public ResponseEntity<?> getRole(@PathVariable String role) {
        return new ResponseEntity<>(service.findRole(role), HttpStatus.OK);
    }
}
