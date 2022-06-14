package com.github.enid3.questionnaire.service.impl;

import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.entity.Questionnaire;
import com.github.enid3.questionnaire.data.entity.User;
import com.github.enid3.questionnaire.data.repository.UserRepository;
import com.github.enid3.questionnaire.service.exception.user.InvalidUserException;
import com.github.enid3.questionnaire.service.exception.user.UserExceptionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnershipServiceImplTest {
    @Mock
    private UserRepository userRepository;

    private UserExceptionFactory userExceptionFactory = new UserExceptionFactory(
            Mockito.mock(MessageSource.class),
            Locale.ENGLISH);

    private OwnershipServiceImpl ownershipService;

    private static String NON_EXISTING_EMAIL = "non@existing.com";

    private static final List<Field> fields = List.of(
            Field.builder()
                    .id(1L)
                    .label("singleline r a")
                    .type(Field.Type.SINGLE_LINE_TEXT)
                    .isRequired(true)
                    .isActive(true)
                    .build(),
            Field.builder()
                    .id(2L)
                    .label("multiline a")
                    .type(Field.Type.MULTILINE_TEXT)
                    .isRequired(false)
                    .isActive(true)
                    .build(),
            Field.builder()
                    .id(3L)
                    .label("checkboxes a")
                    .type(Field.Type.CHECKBOX)
                    .options(Set.of("A", "B", "C"))
                    .isRequired(false)
                    .isActive(true)
                    .build()
    );

    private User user = User.builder()
            .id(123L)
            .firstName("Y")
            .lastName("S")
            .email("email@eml.com")
            .password("p@ssw0rd_h@sh")
            .phoneNumber("+1111111111")
            .build();

    private Questionnaire questionnaire = Questionnaire.builder()
            .id(123L)
            .name("some label")
            .description("some long long long description")
            .fields(fields)
            .build();

    @BeforeEach
    void init() {
        this.ownershipService = new OwnershipServiceImpl(userRepository, userExceptionFactory);
    }

    @Test
    void setOwnerShouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findByEmail(NON_EXISTING_EMAIL)).thenReturn(Optional.empty());

        InvalidUserException ex = assertThrows(InvalidUserException.class,
                () -> ownershipService.setOwner(NON_EXISTING_EMAIL, questionnaire));

        assertEquals(InvalidUserException.Reason.USER_NOT_FOUND, ex.getReason());
    }

    @Test
    void setOwnerShouldSetOwnerPropertyForFieldWhenUserFound() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        ownershipService.setOwner(user.getEmail(), questionnaire);
        assertEquals(user, questionnaire.getOwner());
    }

    @Test
    void setOwnerShouldNotChangeOwnerPropertyForFieldWhenUserNotFound() {
        when(userRepository.findByEmail(NON_EXISTING_EMAIL)).thenReturn(Optional.empty());

        User userBeforeSetting = questionnaire.getOwner();

        assertThrows(InvalidUserException.class,
                () -> ownershipService.setOwner(NON_EXISTING_EMAIL, questionnaire));

        assertEquals(userBeforeSetting, questionnaire.getOwner());
    }

}