package com.github.enid3.questionnaire.controller.api.v1;

import com.github.enid3.questionnaire.data.dto.user.auth.AuthDTO;
import com.github.enid3.questionnaire.data.dto.user.auth.AuthSuccessDTO;
import com.github.enid3.questionnaire.data.dto.user.auth.RegisterDTO;
import com.github.enid3.questionnaire.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signin")
    public AuthSuccessDTO signin(@RequestBody @Valid AuthDTO authDTO) {
        return authService.signin(authDTO);
    }

    @PostMapping("/register")
    public AuthSuccessDTO register(@RequestBody @Valid RegisterDTO registerDTO) {
        return authService.register(registerDTO);
    }
}
