package com.swerve.backend.auth.service;

import com.swerve.backend.auth.dto.TokensDTO;
import com.swerve.backend.auth.mapper.UserMapper;
import com.swerve.backend.auth.model.User;
import com.swerve.backend.auth.repository.UserRepository;
import com.swerve.backend.auth.security.TokenGenerator;
import com.swerve.backend.shared.dto.UserDTO;
import com.swerve.backend.shared.dto.UserDetailsDTO;
import com.swerve.backend.shared.exception.ForbiddenException;
import com.swerve.backend.shared.exception.NotFoundException;
import com.swerve.backend.shared.service.BaseService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.swerve.backend.shared.security.SecurityUtils.*;

@Service
public class UserService extends BaseService<User, UserDetailsDTO, Long> {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final UserDetailsService userDetailsService;
    private final TokenGenerator tokenGenerator;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository repository,
            UserMapper mapper,
            UserDetailsService userDetailsService,
            TokenGenerator tokenGenerator,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
        this.userDetailsService = userDetailsService;
        this.tokenGenerator = tokenGenerator;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetailsDTO save(UserDetailsDTO userDetailsDTO) {
        userDetailsDTO.setPassword(passwordEncoder.encode(userDetailsDTO.getPassword()));
        userDetailsDTO.setAccountNonExpired(true);
        userDetailsDTO.setAccountNonLocked(true);
        userDetailsDTO.setCredentialsNonExpired(true);
        userDetailsDTO.setEnabled(true);
        return super.save(userDetailsDTO);
    }

    @Transactional
    public UserDetailsDTO update(UserDetailsDTO userDetailsDTO) {
        User existingUser =
                repository
                        .findById(userDetailsDTO.getId())
                        .orElseThrow(() -> new NotFoundException("User not found"));
        if (userDetailsDTO.getUsername() != null) {
            existingUser.setUsername(userDetailsDTO.getUsername());
        }
        if (userDetailsDTO.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(userDetailsDTO.getPassword()));
        }

        return this.mapper.toDTO(this.repository.save(existingUser));
    }

    public List<UserDTO> findByIdPublic(Set<Long> id) {
        List<User> users = (List<User>) this.repository.findAllById(id);
        if (users.isEmpty()) {
            throw new NotFoundException("User id not found");
        }
        return this.mapper.userToUserDTOList(users);
    }

    public UserDetailsDTO findByUsername(String username) throws UsernameNotFoundException {
        if (!getUsername().equals(username) && !hasAuthority(ROLE_ADMIN)) {
            throw new ForbiddenException("You are not allowed to view this user's details");
        }
        System.out.println(username);
        return (UserDetailsDTO) userDetailsService.loadUserByUsername(username);
    }

    public Long findIdByUsername(String username) {
        return this.repository
                .findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Username not found"))
                .getId();
    }

    public TokensDTO login(UserDTO userDTO) {
        System.out.println(userDTO.getUsername());
        System.out.println(userDTO.getPassword());
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        userDTO.getUsername(), userDTO.getPassword());
        System.out.println(token);
        Authentication authentication = authenticationManager.authenticate(token);
        System.out.println(userDTO.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println(userDTO.getPassword());
        String username = userDTO.getUsername();
        System.out.println(userDTO.getPassword());
        return new TokensDTO(
                tokenGenerator.generateAccessToken(username),
                tokenGenerator.generateRefreshToken(username));
    }

    public TokensDTO refresh(String refreshToken) {
        System.out.println(refreshToken);
        refreshToken = refreshToken.substring(BEARER_PREFIX.length());
        System.out.println(refreshToken);
        return new TokensDTO(tokenGenerator.refreshAccessToken(refreshToken), refreshToken);
    }
}
