package com.swerve.backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleCustomizeDTO {
    private Long userId;
    private String username;
    private String password;
    private String portal;
    private String authority;
    private List<String> system_features;
}
