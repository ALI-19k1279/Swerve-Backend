package com.swerve.backend.auth.controller;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;


@RestController
@RequestMapping("/roles")
public class RoleController extends BaseController<Role, RoleDTO, Long> {
    private final RoleService service;
    private final PasswordEncoder passwordEncoder;

    public RoleController(RoleService service, PasswordEncoder passwordEncoder) {
        super(service);
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/customise-user-type")
    public ResponseEntity<?> customizeUserRoles(@RequestBody RoleCustomizeDTO rolePayloadDTO) {
        try {
            Long userId= rolePayloadDTO.getUserId();
            String username=rolePayloadDTO.getUsername();
            String password=passwordEncoder.encode(rolePayloadDTO.getPassword());
            String portal=rolePayloadDTO.getPortal();
            String roleName = rolePayloadDTO.getAuthority();
            List<String> authorities = rolePayloadDTO.getSystem_features();


            System.out.println("Role Name: " + roleName);
            System.out.println("Authorities: " + authorities);


            service.customizeUserRoles(roleName, authorities,userId,username,password,portal);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


}
