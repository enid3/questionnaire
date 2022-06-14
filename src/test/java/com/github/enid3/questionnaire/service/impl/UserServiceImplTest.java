package com.github.enid3.questionnaire.service.impl;

import com.github.enid3.questionnaire.data.dto.user.UserDTO;
import com.github.enid3.questionnaire.data.dto.user.UserResponseDTO;
import com.github.enid3.questionnaire.data.dto.user.UserUpdateDTO;
import com.github.enid3.questionnaire.data.entity.User;
import com.github.enid3.questionnaire.data.mapper.UserMapper;
import com.github.enid3.questionnaire.data.repository.UserRepository;
import com.github.enid3.questionnaire.service.exception.user.InvalidUserException;
import com.github.enid3.questionnaire.service.exception.user.UserExceptionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private UserExceptionFactory userExceptionFactory = new UserExceptionFactory(
            Mockito.mock(MessageSource.class),
            Locale.ENGLISH);

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private UserServiceImpl userService;

    private static final String NON_EXISTING_USERS_EMAIL = "some@email.gov";

    private User user = User.builder()
            .id(123L)
            .firstName("Y")
            .lastName("S")
            .email("email@eml.com")
            .password("p@ssw0rd_h@sh")
            .phoneNumber("+1111111111")
            .build();

    private UserDTO userDTO = userMapper.toUserDTO(user);

    private UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
            .firstName("Yahor")
            .build();

    @BeforeEach
    void init() {
        this.userService = new UserServiceImpl(
                userRepository,
                userMapper,
                userExceptionFactory,
                passwordEncoder
        );
    }


    @Test
    void getUserShouldThrowExceptionWhenUserNotExists() {
        when(userRepository.findByEmail(NON_EXISTING_USERS_EMAIL)).thenReturn(Optional.empty());

        InvalidUserException ex = assertThrows(InvalidUserException.class,
                () -> userService.getUser(NON_EXISTING_USERS_EMAIL));

        assertEquals(InvalidUserException.Reason.USER_NOT_FOUND, ex.getReason());
    }

    @Test
    void getUserShouldReturnCorrectUserResponseDtoWhenUserExists() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserResponseDTO userResponseDTO = userService.getUser(user.getEmail());

        assertEquals(user.getId(), userResponseDTO.getId());
        assertEquals(user.getEmail(), userResponseDTO.getEmail());
        assertEquals(user.getFirstName(), userResponseDTO.getFirstName());
        assertEquals(user.getLastName(), userResponseDTO.getLastName());
        assertEquals(user.getPhoneNumber(), userResponseDTO.getPhoneNumber());
    }

    @Test
    void createUserShouldSaveUserWithEncodedPassword() {
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.createUser(userDTO);
        verify(userRepository).save(userArgumentCaptor.capture());
        User userToSave = userArgumentCaptor.getValue();

        assertTrue(passwordEncoder.matches(userDTO.getPassword(), userToSave.getPassword()));
    }

    @Test
    void createUserShouldNotSpecifyIdWhenSaving() {
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.createUser(userDTO);
        verify(userRepository).save(userArgumentCaptor.capture());
        User userToSave = userArgumentCaptor.getValue();

        assertNull(userToSave.getId());
    }

    @Test
    void createUserShouldThrowExceptionWhenEmailAlreadyInUse() {
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(user));

        InvalidUserException ex = assertThrows(InvalidUserException.class,
                () -> userService.createUser(userDTO));
        assertEquals(InvalidUserException.Reason.USER_EMAIL_ALREADY_IN_USE, ex.getReason());
    }

    @Test
    void updateUserShouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findByEmail(NON_EXISTING_USERS_EMAIL)).thenReturn(Optional.empty());

        InvalidUserException ex = assertThrows(InvalidUserException.class,
                () -> userService.updateUser(NON_EXISTING_USERS_EMAIL, userUpdateDTO));

        assertEquals(InvalidUserException.Reason.USER_NOT_FOUND, ex.getReason());
    }

    @Test
    void updateUserShouldChangeNotNullFieldsOnly() {
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.updateUser(user.getEmail(), userUpdateDTO);
        verify(userRepository).save(userArgumentCaptor.capture());
        User userToSave = userArgumentCaptor.getValue();

        // Null fields
        assertEquals(user.getId(), userToSave.getId());
        assertEquals(user.getEmail(), userToSave.getEmail());
        assertEquals(user.getPassword(), userToSave.getPassword());
        assertEquals(user.getLastName(), userToSave.getLastName());
        assertEquals(user.getPhoneNumber(), userToSave.getPhoneNumber());

        // Not null fields
        assertEquals(userUpdateDTO.getFirstName(), userToSave.getFirstName());
    }

    @Test
    void changeUserPasswordShouldEncodePassword() {
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        String newPassword = "n3w_pa$$";

        userService.changeUserPassword(user.getEmail(), newPassword);
        verify(userRepository).save(userArgumentCaptor.capture());
        User userToSave = userArgumentCaptor.getValue();

        assertTrue(passwordEncoder.matches(newPassword, userToSave.getPassword()));
    }

    @Test
    void changeUserPasswordShouldThrowExceptionWhenUserNotExists() {
        when(userRepository.findByEmail(NON_EXISTING_USERS_EMAIL)).thenReturn(Optional.empty());

        InvalidUserException ex = assertThrows(InvalidUserException.class,
                () -> userService.getUser(NON_EXISTING_USERS_EMAIL));

        assertEquals(InvalidUserException.Reason.USER_NOT_FOUND, ex.getReason());
    }

    @Test
    void loadUserByUsernameShouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findByEmail(NON_EXISTING_USERS_EMAIL)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername(NON_EXISTING_USERS_EMAIL));
    }

    // fixme add tests for List<Questionnaire>
}