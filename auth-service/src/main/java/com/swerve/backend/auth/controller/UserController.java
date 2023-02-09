package com.swerve.backend.auth.controller;

import com.swerve.backend.auth.model.User;
import com.swerve.backend.auth.service.UserService;
import com.swerve.backend.shared.controller.BaseController;
import com.swerve.backend.shared.dto.UserDTO;
import com.swerve.backend.shared.dto.UserDetailsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController<User, UserDetailsDTO, Long> {
    private final UserService service;

    public UserController(UserService service) {
        super(service);
        this.service = service;
    }

    @GetMapping("/")
    public String getPuc(@PathVariable Set<Long> id) {
        return "hello";
    }
    @GetMapping("/{id}/public")
    public ResponseEntity<List<UserDTO>> getPublic(@PathVariable Set<Long> id) {
        System.out.println(id);
        return new ResponseEntity<>(service.findByIdPublic(id), HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDetails> getUserByUsername(@PathVariable String username) {
        System.out.println("Hello");
        System.out.println(username);
        return new ResponseEntity<>(service.findByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/username/{username}/id")
    public ResponseEntity<Long> getUserIdByUsername(@PathVariable String username) {
        return new ResponseEntity<>(service.findIdByUsername(username), HttpStatus.OK);
    }

    @PatchMapping({"/{id}"})
    public ResponseEntity<UserDetailsDTO> patch(
            @PathVariable Long id, @RequestBody UserDetailsDTO DTO) {
        System.out.println(id);
        DTO.setId(id);
        return new ResponseEntity<>(this.service.update(DTO), HttpStatus.OK);
    }
}
