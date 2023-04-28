package com.swerve.backend.userservice.config;

import com.swerve.backend.shared.security.AuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.swerve.backend.shared.security.SecurityUtils.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationTokenFilter authenticationTokenFilter)
            throws Exception {
        return http
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers(
                        HttpMethod.GET,
                        "/actuator/**",
                        "/docs/**").permitAll()
                .requestMatchers(
                        HttpMethod.GET,
                        "/faculties/**",
                        "/study-programs/**",
                        "/teachers/**",
                        "/theses/**",
                        "/addresses/**",
                        "/cities/**",
                        "/countries/**",
                        "/*/user-id/*/id").permitAll()
                .requestMatchers(
                        HttpMethod.GET,
                        "/students/*").hasAnyAuthority(ROLE_STUDENT, ROLE_TEACHER, ROLE_ADMIN)
                .requestMatchers(
                        HttpMethod.GET,
                        "/students/**").hasAnyAuthority(ROLE_TEACHER, ROLE_ADMIN)
                .requestMatchers(
                        HttpMethod.POST,
                        "/batch/importstudents"
                ).permitAll()
                .anyRequest().hasAuthority(ROLE_ADMIN)
                .and()
                .build();
    }
}
