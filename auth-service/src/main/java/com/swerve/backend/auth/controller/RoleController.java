package com.swerve.backend.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.swerve.backend.auth.dto.RoleCustomizeDTO;
import com.swerve.backend.auth.model.Role;
import com.swerve.backend.auth.service.RoleService;
import com.swerve.backend.shared.controller.BaseController;
import com.swerve.backend.shared.dto.RoleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/roles")
public class RoleController extends BaseController<Role, RoleDTO, Long> {
    private final RoleService service;

    public RoleController(RoleService service) {
        super(service);
        this.service = service;
    }
    @PostMapping("/customise-user-type")
    public ResponseEntity<?> customizeUserRoles(@RequestBody RoleCustomizeDTO rolePayloadDTO) {
        try {
            String roleName = rolePayloadDTO.getAuthority();
            List<String> authorities = rolePayloadDTO.getSystem_features();

            // Perform operations with the deserialized data
            System.out.println("Role Name: " + roleName);
            System.out.println("Authorities: " + authorities);

            // Call your service method with the extracted data
            service.customizeUserRoles(roleName, authorities);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


}
