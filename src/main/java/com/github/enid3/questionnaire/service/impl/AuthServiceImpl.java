package com.github.enid3.questionnaire.service.impl;

import com.github.enid3.questionnaire.data.dto.user.UserResponseDTO;
import com.github.enid3.questionnaire.data.dto.user.auth.AuthDTO;
import com.github.enid3.questionnaire.data.dto.user.auth.AuthSuccessDTO;
import com.github.enid3.questionnaire.data.dto.user.auth.RegisterDTO;
import com.github.enid3.questionnaire.data.mapper.UserMapper;
import com.github.enid3.questionnaire.security.jwt.JwtTokenProvider;
import com.github.enid3.questionnaire.service.AuthService;
import com.github.enid3.questionnaire.service.UserService;
import com.github.enid3.questionnaire.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final UserMapper userMapper;

    private String authenticate(String email, String password) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        return jwtTokenProvider.createToken(authentication);
    }

    @Override
    public AuthSuccessDTO signin(AuthDTO authRequestDTO) {
        try {
            String token = authenticate(authRequestDTO.getEmail(), authRequestDTO.getPassword());

            UserResponseDTO user = userService.getUser(authRequestDTO.getEmail());
            return AuthSuccessDTO.builder()
                    .user(user)
                    .token(token)
                    .build();
        } catch (AuthenticationException | ServiceException ex ) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @Override
    @Transactional
    public AuthSuccessDTO register(RegisterDTO registerDTO) {
        try {
            UserResponseDTO user = userService.createUser(userMapper.toUserDTO(registerDTO));
            String token = authenticate(registerDTO.getEmail(), registerDTO.getPassword());

            return AuthSuccessDTO.builder()
                    .user(user)
                    .token(token)
                    .build();
        }
        catch (ServiceException ex) {
            throw new BadCredentialsException("Failed to register", ex);
        }
    }
}
