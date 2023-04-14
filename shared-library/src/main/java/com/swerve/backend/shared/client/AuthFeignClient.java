package com.swerve.backend.shared.client;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.swerve.backend.shared.dto.UserDetailsDTO;
import com.swerve.backend.shared.util.UserDetailsDeserializer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "auth-service", contextId = "authFeignClient")
@FeignClient(value = "auth-service", url = "http://localhost:8081/users")
@JsonDeserialize(using = UserDetailsDeserializer.class)
public interface AuthFeignClient {
    @GetMapping("/username/{username}")
    ResponseEntity<UserDetailsDTO> getUser(@PathVariable String username);
}
