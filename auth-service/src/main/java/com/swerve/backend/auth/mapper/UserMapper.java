package com.swerve.backend.auth.mapper;

import com.swerve.backend.auth.model.User;
import com.swerve.backend.shared.dto.UserDTO;
import com.swerve.backend.shared.dto.UserDetailsDTO;
import com.swerve.backend.shared.mapper.BaseMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserDetailsDTO, Long> {
    UserDTO userToUserDTO(User user);

    User userDTOtoUser(UserDTO userDTO);

    List<UserDTO> userToUserDTOList(List<User> users);

    List<User> userDTOtoUserList(List<UserDTO> userDTOList);
}
