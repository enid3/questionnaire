package com.github.enid3.questionnaire.controller.api.v1;

import com.github.enid3.questionnaire.data.dto.AuthDTO;
import com.github.enid3.questionnaire.data.entity.User;
import com.github.enid3.questionnaire.data.repository.UserRepository;
import com.github.enid3.questionnaire.security.jwt.JwtTokenProvider;
import com.github.enid3.questionnaire.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserService userService;

    @PostMapping("/signin")
    public AuthDTO signin(@RequestBody AuthenticationRequest data) {
        try {
            String email = data.getEmail();
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, data.getPassword()));

            String token = jwtTokenProvider.createToken(authentication);

            AuthDTO dto = new AuthDTO();
            dto.setToken(token);
            User user = userRepository.findByEmail(email);
            user.setPassword(null);
            dto.setUser(user);
            return dto;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("/register")
    public AuthDTO register(@RequestBody User user) {
        if (userService.register(user)) {
            String email = user.getEmail();
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, user.getPassword()));

            String token = jwtTokenProvider.createToken(authentication);

            AuthDTO dto = new AuthDTO();
            dto.setToken(token);
            user.setPassword(null);
            dto.setUser(user);
            return dto;
        }
        log.info("failed to register: {}", user);
        throw new BadCredentialsException("Bad credials exception");
    }
}
