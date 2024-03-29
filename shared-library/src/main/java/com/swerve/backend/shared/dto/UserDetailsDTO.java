package com.swerve.backend.shared.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.swerve.backend.shared.util.UserDetailsDeserializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@JsonDeserialize(using = UserDetailsDeserializer.class)
public class UserDetailsDTO extends BaseDTO<Long> implements UserDetails {
    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    private String portal;

    @NotEmpty(message = "Authorities are mandatory")
    private Set<RoleDTO> roles;

    private Collection<? extends GrantedAuthority> authorities;
}
