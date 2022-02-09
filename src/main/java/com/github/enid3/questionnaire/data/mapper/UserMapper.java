package com.github.enid3.questionnaire.data.mapper;

import com.github.enid3.questionnaire.data.dto.user.auth.RegisterDTO;
import com.github.enid3.questionnaire.data.dto.user.UserDTO;
import com.github.enid3.questionnaire.data.dto.user.UserResponseDTO;
import com.github.enid3.questionnaire.data.dto.user.UserUpdateDTO;
import com.github.enid3.questionnaire.data.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toUserDTO(User user);

    @Mapping(target = "id", ignore = true)
    UserDTO toUserDTO(RegisterDTO registerDTO);
    User toUser(UserDTO userDTO);

    UserResponseDTO toUserResponseDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mergeUser(@MappingTarget User user, UserUpdateDTO userUpdateDTO);
}
