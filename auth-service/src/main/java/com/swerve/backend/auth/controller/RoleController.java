package com.swerve.backend.auth.controller;

import com.swerve.backend.auth.model.Role;
import com.swerve.backend.auth.service.RoleService;
import com.swerve.backend.shared.controller.BaseController;
import com.swerve.backend.shared.dto.RoleDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/roles")
public class RoleController extends BaseController<Role, RoleDTO, Long> {
    private final RoleService service;

    public RoleController(RoleService service) {
        super(service);
        this.service = service;
    }
}
