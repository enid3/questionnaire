package com.github.enid3.questionnaire.service.impl;

import com.github.enid3.questionnaire.data.dto.user.UserDTO;
import com.github.enid3.questionnaire.data.dto.user.UserResponseDTO;
import com.github.enid3.questionnaire.data.dto.user.UserUpdateDTO;
import com.github.enid3.questionnaire.data.entity.User;
import com.github.enid3.questionnaire.data.mapper.UserMapper;
import com.github.enid3.questionnaire.data.repository.UserRepository;
import com.github.enid3.questionnaire.service.UserService;
import com.github.enid3.questionnaire.service.exception.ServiceException;
import com.github.enid3.questionnaire.service.exception.user.UserExceptionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final UserExceptionFactory userExceptionFactory;

    private final PasswordEncoder passwordEncoder;

    private String encodePassword(String plainPassowrd) {
        return passwordEncoder.encode(plainPassowrd);
    }


    @Override
    public UserResponseDTO getUser(String email) {
        Optional<User> optionalUser;
        try {
            optionalUser = userRepository.findByEmail(email);
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
        return optionalUser
                .map(userMapper::toUserResponseDTO)
                .orElseThrow(() -> userExceptionFactory.createUserNotFoundException(email));

    }

    @Override
    public UserResponseDTO createUser(UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        user.setId(null);
        user.setPassword(encodePassword(user.getPassword()));

        try {
            User createdUser = userRepository.save(user);
            return userMapper.toUserResponseDTO(createdUser);
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public UserResponseDTO updateUser(String email, UserUpdateDTO userUpdateDTO) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            User user = userOptional.orElseThrow(() -> userExceptionFactory.createUserNotFoundException(email));

            userMapper.mergeUser(user, userUpdateDTO);
            userRepository.save(user);

            return userMapper.toUserResponseDTO(user);
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public boolean userExists(long id) {
        try {
            return userRepository.existsById(id);
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public boolean changeUserPassword(String email, String newPassword) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            User user = userOptional.orElseThrow(() -> userExceptionFactory.createUserNotFoundException(email));

            user.setPassword(encodePassword(newPassword));

            userRepository.save(user);
            return true;
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User with email: '" + email + "' not found."));

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    authorities
            );
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }
}
