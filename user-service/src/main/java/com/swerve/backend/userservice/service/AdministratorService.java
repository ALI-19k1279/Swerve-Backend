package com.swerve.backend.userservice.service;

import com.swerve.backend.userservice.client.UserFeignClient;
import com.swerve.backend.userservice.dto.AdministratorDTO;
import com.swerve.backend.userservice.mapper.AdministratorMapper;
import com.swerve.backend.userservice.model.Administrator;
import com.swerve.backend.userservice.repository.AdministratorRepository;
import com.swerve.backend.shared.dto.RoleDTO;
import com.swerve.backend.shared.dto.UserDTO;
import com.swerve.backend.shared.dto.UserDetailsDTO;
import com.swerve.backend.shared.exception.NotFoundException;
import com.swerve.backend.shared.service.ExtendedService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.swerve.backend.shared.security.SecurityUtils.ROLE_ADMIN;
import static com.swerve.backend.shared.security.SecurityUtils.ROLE_ADMIN_ID;

@Service
public class AdministratorService extends ExtendedService<Administrator, AdministratorDTO, Long> {
    private final AdministratorRepository repository;
    private final AdministratorMapper mapper;
    private final UserFeignClient userFeignClient;

    public AdministratorService(
            AdministratorRepository repository,
            AdministratorMapper mapper,
            UserFeignClient userFeignClient) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
        this.userFeignClient = userFeignClient;
    }

    @Override
    @Transactional
    public AdministratorDTO save(AdministratorDTO administrator) {
        UserDTO userRequest = administrator.getUser();
        UserDTO userResponse =
                userRequest.getId() == null
                        ? userFeignClient.createUser(
                                UserDetailsDTO.builder()
                                        .username(userRequest.getUsername())
                                        .password(userRequest.getPassword())
                                        .authorities(
                                                Set.of(
                                                        RoleDTO.builder()
                                                                .id(ROLE_ADMIN_ID)
                                                                .authority(ROLE_ADMIN)
                                                                .build()))
                                        .build())
                        : userFeignClient.patchUser(userRequest.getId(), userRequest);
        administrator.setUser(userResponse);
        return super.save(administrator);
    }

    @Override
    @Transactional
    public void delete(Set<Long> id) {
        List<Administrator> administrators = (List<Administrator>) repository.findAllById(id);
        Set<Long> userIds =
                administrators.stream().map(Administrator::getUserId).collect(Collectors.toSet());
        userFeignClient.deleteUser(userIds);
        repository.softDeleteByIds(id);
    }

    @Override
    protected List<AdministratorDTO> mapMissingValues(List<AdministratorDTO> administrators) {
        map(
                administrators,
                AdministratorDTO::getUser,
                AdministratorDTO::setUser,
                userFeignClient::getUser);

        return administrators;
    }

    public AdministratorDTO findByUserId(Long userId) {
        Administrator administrator =
                repository
                        .findByUserId(userId)
                        .orElseThrow(() -> new NotFoundException("User id not found"));
        System.out.println(administrator.getFirstName());
        return mapper.toDTO(administrator);
    }
}
