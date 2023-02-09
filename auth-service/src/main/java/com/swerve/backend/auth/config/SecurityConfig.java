package com.swerve.backend.auth.config;

import com.swerve.backend.auth.security.AuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.swerve.backend.shared.security.SecurityUtils.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthTokenFilter authTokenFilter)
            throws Exception {
        return http
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers(
                        HttpMethod.GET,
                        "/actuator/**",
                        "/refresh").permitAll()
                .requestMatchers(
                        HttpMethod.POST,
                        "/login").anonymous()
                .requestMatchers(
                        HttpMethod.GET,
                        "/users/username/*/id",
                        "/users/**/public").permitAll()
                .requestMatchers(
                        HttpMethod.GET,
                        "/users/username/*").authenticated()
                .requestMatchers("/users/**").hasAuthority(ROLE_ADMIN)
                .anyRequest().hasAuthority(ROLE_ROOT)
                .and()
                .build();
    }
}
