package com.github.enid3.questionnaire.data.dto;

import com.github.enid3.questionnaire.data.entity.User;
import lombok.Data;

@Data
public class AuthDTO {
    String token;
    User user;
}
