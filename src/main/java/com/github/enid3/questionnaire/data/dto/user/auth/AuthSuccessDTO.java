package com.github.enid3.questionnaire.data.dto.user.auth;

import com.github.enid3.questionnaire.data.dto.user.UserResponseDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthSuccessDTO {
    String token;
    UserResponseDTO user;
}
