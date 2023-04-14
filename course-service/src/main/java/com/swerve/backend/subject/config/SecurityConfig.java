package com.swerve.backend.subject.config;

import com.swerve.backend.shared.security.AuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.swerve.backend.shared.security.SecurityUtils.*;

@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
//                .requestMatchers(
//                        HttpMethod.GET,
//                        "/subjects/student/*/all").hasAnyAuthority(ROLE_STUDENT, ROLE_ADMIN)
                .requestMatchers(
                        HttpMethod.GET,
                        "cycles/*/cycle",
                "/courses/*/learningtrack",
                        "/courses/*/prereq",
                        "/courses/*/course",
                        "/courses/offeredcourses",
                        "/spgoc/*/offeredcourses/gid",
                        "/spgoc/*/offeredcourses/stdid",
                        "/spgoc/*/*/*/evaluationitems",
                        "/spgoc/*/*/evaluations/stdid",
                        "/spgoc/*/students").permitAll()
                .requestMatchers(
                        HttpMethod.POST,
                        "/courses/create"
                ).permitAll()
//                .requestMatchers(
//                        HttpMethod.GET,
//                        "/subject-enrollments/*",
//                        "/subject-enrollments/student/*/average-grade",
//                        "/subject-enrollments/student/*/total-ects").hasAnyAuthority(ROLE_STUDENT, ROLE_TEACHER, ROLE_ADMIN)
//                .requestMatchers(
//                        HttpMethod.GET,
//                        "/subject-enrollments/student/**").hasAnyAuthority(ROLE_STUDENT, ROLE_ADMIN)
//                .requestMatchers(
//                        HttpMethod.GET,
//                        "/subject-enrollments/subject/*",
//                        "/subject-enrollments/subject/*/student-id/all").hasAnyAuthority(ROLE_TEACHER, ROLE_ADMIN)
//                .requestMatchers(
//                        HttpMethod.PATCH,
//                        "/subjects/*/syllabus",
//                        "/subject-enrollments/*/grade").hasAnyAuthority(ROLE_TEACHER, ROLE_ADMIN)
//                .requestMatchers(
//                        "/subject-materials/**",
//                        "/subject-notifications/**",
//                        "/subject-terms/**").hasAnyAuthority(ROLE_TEACHER, ROLE_ADMIN)
                .requestMatchers(
                        HttpMethod.GET,
                        "/hello"

                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .build();
    }
}
