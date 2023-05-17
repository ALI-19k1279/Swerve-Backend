//package com.swerve.backend.auth;
//
//import com.swerve.backend.auth.service.RoleService;
//import com.swerve.backend.auth.service.UserService;
//import com.swerve.backend.shared.dto.RoleDTO;
//import com.swerve.backend.shared.dto.UserDetailsDTO;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.Set;
//
//import static com.swerve.backend.shared.security.SecurityUtils.*;
//
//@Component
//@RequiredArgsConstructor
//public class AppStartupRunner implements ApplicationRunner {
//    private final RoleService roleService;
//    private final UserService userService;
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(ApplicationArguments args) {
//        RoleDTO root = RoleDTO.builder().id(ROLE_ROOT_ID).authority(ROLE_ROOT).build();
//        RoleDTO admin = RoleDTO.builder().id(ROLE_ADMIN_ID).authority(ROLE_ADMIN).build();
//        RoleDTO teacher = RoleDTO.builder().id(ROLE_TEACHER_ID).authority(ROLE_TEACHER).build();
//        RoleDTO student = RoleDTO.builder().id(ROLE_STUDENT_ID).authority(ROLE_STUDENT).build();
//
//        UserDetailsDTO user =
//                UserDetailsDTO.builder()
//                        .id(ROOT_USER_ID)
//                        .username("std@swerve.com")
//                        .password(passwordEncoder.encode("password"))
//                        .portal("learner")
//                        .roles(Set.of(root, admin))
//                        .accountNonExpired(true)
//                        .accountNonLocked(true)
//                        .credentialsNonExpired(true)
//                        .enabled(true)
//                        .build();
//
//        if (roleService.findAll().isEmpty()) {
//            roleService.forceSave(root);
//            roleService.forceSave(admin);
//            roleService.forceSave(teacher);
//            roleService.forceSave(student);
//        }
//
//        if (userService.findAll().isEmpty()) {
//            userService.forceSave(user);
//        }
//    }
//}
