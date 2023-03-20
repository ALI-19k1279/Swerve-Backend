package com.swerve.backend.userservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients({"com.swerve.backend.userservice", "com.swerve.backend.shared"})
@ComponentScan({"com.swerve.backend.userservice", "com.swerve.backend.shared"})
@OpenAPIDefinition(
		info =
		@Info(
				title = "User API",
				version = "1.0",
				description = "Documentation User API v1.0"))
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
