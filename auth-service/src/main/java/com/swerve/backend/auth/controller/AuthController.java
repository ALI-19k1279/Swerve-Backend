package com.swerve.backend.auth.controller;

import com.swerve.backend.auth.dto.TokensDTO;
import com.swerve.backend.auth.service.UserService;
import com.swerve.backend.shared.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokensDTO> login(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.login(userDTO), HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<TokensDTO> refresh(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken) {
        System.out.println(refreshToken);
        return new ResponseEntity<>(userService.refresh(refreshToken), HttpStatus.OK);
    }
}
