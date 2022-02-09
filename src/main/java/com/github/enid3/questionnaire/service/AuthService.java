package com.github.enid3.questionnaire.service;

import com.github.enid3.questionnaire.data.dto.user.auth.AuthDTO;
import com.github.enid3.questionnaire.data.dto.user.auth.AuthSuccessDTO;
import com.github.enid3.questionnaire.data.dto.user.auth.RegisterDTO;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface AuthService {
    AuthSuccessDTO signin(@Valid AuthDTO authRequestDTO);
    AuthSuccessDTO register(@Valid RegisterDTO registerDTO);
}
