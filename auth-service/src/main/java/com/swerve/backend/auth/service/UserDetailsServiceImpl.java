package com.swerve.backend.auth.service;

import com.swerve.backend.auth.mapper.UserMapper;
import com.swerve.backend.auth.model.Role;
import com.swerve.backend.auth.model.User;
import com.swerve.backend.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        if(user.isPresent()){
            System.out.println("start of userDetailsServiceImpl");
            List<String> authoritiesList =
                    user.get().getRoles().stream().map(GrantedAuthority::getAuthority).toList();
            System.out.println(authoritiesList.isEmpty());
            for(String role:authoritiesList){
                System.out.println("roles");
                System.out.println(role);
            }
            List<String> description= repository.findByRolesAuthorityIn(authoritiesList);
            for(String desc:description){
                System.out.println(desc);
            }
            Set<SimpleGrantedAuthority> permissions = description.stream()
                    .map(permission -> new SimpleGrantedAuthority(permission))
                    .collect(Collectors.toSet());
            for(String role:authoritiesList){
                permissions.add(new SimpleGrantedAuthority(role));
            }
            user.get().setAuthorities(permissions);
            System.out.println("in userDetailsServiceImpl");

        }



        return mapper.toDTO(user.get());
    }
}
