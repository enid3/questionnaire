package com.github.enid3.questionnaire.service;

import com.github.enid3.questionnaire.data.dto.user.UserDTO;
import com.github.enid3.questionnaire.data.dto.user.UserResponseDTO;
import com.github.enid3.questionnaire.data.dto.user.UserUpdateDTO;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserService {
    UserResponseDTO getUser(String email);
    UserResponseDTO createUser(UserDTO userDTO);
    UserResponseDTO updateUser(String email, UserUpdateDTO userUpdateDTO);
    boolean changeUserPassword(String email, String newPassword);
    boolean userExists(long id);
}
