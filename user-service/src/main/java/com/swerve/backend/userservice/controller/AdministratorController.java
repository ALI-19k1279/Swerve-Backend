package com.swerve.backend.userservice.controller;

import com.swerve.backend.userservice.dto.AdministratorDTO;
import com.swerve.backend.userservice.model.Administrator;
import com.swerve.backend.shared.controller.BaseController;
import com.swerve.backend.userservice.service.AdministratorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/administrators")
public class AdministratorController extends BaseController<Administrator, AdministratorDTO, Long> {
    private final AdministratorService service;

    public AdministratorController(AdministratorService service) {
        super(service);
        this.service = service;
    }

    @GetMapping("/user-id/{id}/id")
    public ResponseEntity<Long> getIdByUserId(@PathVariable Long id) {
        return new ResponseEntity<>(this.service.findByUserId(id).getId(), HttpStatus.OK);
    }
}
