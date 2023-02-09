package com.swerve.backend.auth;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableFeignClients({"com.swerve.backend.auth", "com.swerve.backend.shared"})
@ComponentScan({"com.swerve.backend.auth", "com.swerve.backend.shared"})
@OpenAPIDefinition(
        info =
                @Info(
                        title = "Auth API",
                        version = "1.0",
                        description = "Documentation Auth API v1.0"))
public class AuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}
