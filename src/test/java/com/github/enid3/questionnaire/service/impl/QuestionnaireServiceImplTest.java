package com.github.enid3.questionnaire.service.impl;

import com.github.enid3.questionnaire.data.dto.field.FieldDTO;
import com.github.enid3.questionnaire.data.dto.questionnaire.*;
import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.entity.Questionnaire;
import com.github.enid3.questionnaire.data.entity.User;
import com.github.enid3.questionnaire.data.mapper.FieldMapper;
import com.github.enid3.questionnaire.data.mapper.QuestionnaireMapper;
import com.github.enid3.questionnaire.data.repository.QuestionnaireRepository;
import com.github.enid3.questionnaire.service.OwnershipService;
import com.github.enid3.questionnaire.service.UserService;
import com.github.enid3.questionnaire.service.exception.ServiceException;
import com.github.enid3.questionnaire.service.exception.questionnaire.InvalidQuestionnaireException;
import com.github.enid3.questionnaire.service.exception.questionnaire.QuestionnaireExceptionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionnaireServiceImplTest {
    @Mock
    private QuestionnaireRepository questionnaireRepository;

    private static final QuestionnaireMapper questionnaireMapper = Mappers.getMapper(QuestionnaireMapper.class);
    private static final FieldMapper fieldMapper = Mappers.getMapper(FieldMapper.class);

    private QuestionnaireExceptionFactory exceptionFactory = new QuestionnaireExceptionFactory(
            Mockito.mock(MessageSource.class),
            Locale.ENGLISH);

    @Mock
    private OwnershipService ownershipService;
    @Mock
    private UserService userService;

    QuestionnaireServiceImpl questionnaireService;

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
                    .isActive(false)
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
    private final User owner = User.builder()
            .id(9L)
            .firstName("firstname")
            .lastName("laaastname")
            .email("email@eml.gov")
            .build();

    private final Questionnaire questionnaire = Questionnaire.builder()
            .id(12312L)
            .name("some name")
            .description("some long long long description")
            .fields(fields)
            .owner(owner)
            .build();

    private final QuestionnaireCreateDTO questionnaireCreateDTO = QuestionnaireCreateDTO.builder()
            .name(questionnaire.getName())
            .description(questionnaire.getDescription())
            .build();

    private final QuestionnaireUpdateInfoDTO questionnaireUpdateInfoDTO = QuestionnaireUpdateInfoDTO.builder()
            .name("new name")
            .build();

    private final static long EXISTING_USER_ID = 1L;
    private final static long NON_EXISTING_USER_ID = 88L;
    private final static long NON_EXISTING_QUESTIONNAIRE_ID = 123L;
    private static final long QUESTIONNAIRE_ID = 11L;

    @BeforeEach
    void init() {
        this.questionnaireService = new QuestionnaireServiceImpl(
                questionnaireRepository,
                questionnaireMapper,
                exceptionFactory,
                ownershipService,
                userService
        );
    }

    @Test
    void getQuestionnaireShouldThrowExceptionWhenQuestionnaireNotExists() {
        when(questionnaireRepository.findById(NON_EXISTING_QUESTIONNAIRE_ID)).thenReturn(Optional.empty());

        InvalidQuestionnaireException ex =  assertThrows(InvalidQuestionnaireException.class,
                () -> questionnaireService.getQuestionnaire(NON_EXISTING_QUESTIONNAIRE_ID));

        assertEquals(InvalidQuestionnaireException.Reason.NOT_FOUND, ex.getReason());
    }

    @Test
    void getQuestionnaireShouldReturnCorrectQuestionnaireDtoWhenExists() {
        when(questionnaireRepository.findById(questionnaire.getId())).thenReturn(Optional.of(questionnaire));

        QuestionnaireDTO questionnaireDTO = questionnaireService.getQuestionnaire(questionnaire.getId());

        assertEquals(questionnaire.getId(), questionnaireDTO.getId());
        assertEquals(questionnaire.getName(), questionnaireDTO.getName());
        assertEquals(questionnaire.getDescription(), questionnaireDTO.getDescription());

        List<FieldDTO> fieldDTOS = questionnaire.getFields()
                .stream()
                .map(fieldMapper::toFieldDTO)
                .collect(Collectors.toList());
        assertEquals(new HashSet(fieldDTOS), new HashSet(questionnaireDTO.getFields()));

        assertEquals(questionnaire.getOwner().getId(), questionnaireDTO.getUserInfo().getId());
        assertEquals(questionnaire.getOwner().getFirstName(), questionnaireDTO.getUserInfo().getFirstName());
        assertEquals(questionnaire.getOwner().getLastName(), questionnaireDTO.getUserInfo().getLastName());
    }


    @Test
    void createQuestionnaireShouldSetOwnershipForCreatedQuestionnaire() {
        ArgumentCaptor<Questionnaire> questionnaireArgumentCaptor = ArgumentCaptor.forClass(Questionnaire.class);

        questionnaireService.createQuestionnaire(questionnaire.getOwner().getEmail(), questionnaireCreateDTO);
        Mockito.verify(ownershipService, Mockito.times(1)).setOwner(eq(questionnaire.getOwner().getEmail()), questionnaireArgumentCaptor.capture());
        Questionnaire questionnaireToSetOwner = questionnaireArgumentCaptor.getValue();

        assertEquals(questionnaire.getName(), questionnaireToSetOwner.getName());
        assertEquals(questionnaire.getDescription(), questionnaireToSetOwner.getDescription());
    }

    @Test
    void createQuestionnaireShouldReturnNewDTOWithId() {
        when(questionnaireRepository.save(Mockito.any())).thenReturn(questionnaire);

        QuestionnaireInfoLightDTO dto = questionnaireService.createQuestionnaire(questionnaire.getOwner().getEmail(), questionnaireCreateDTO);

        assertNotNull(dto.getId());
    }

    @Test
    void createQuestionnaireShouldNotPersistWhenSettingOwnerThrowException() {
        doThrow(ServiceException.class).when(ownershipService).setOwner(Mockito.anyString(), Mockito.any(Questionnaire.class));

        assertThrows(ServiceException.class,
                () -> questionnaireService.createQuestionnaire(questionnaire.getOwner().getEmail(), questionnaireCreateDTO));
        verify(questionnaireRepository, times(0)).save(any(Questionnaire.class));
    }

    @Test
    void getAllQuestionnairesInfoByOwnerShouldThrowExceptionWhenUserNotExist() {
        when(userService.userExists(NON_EXISTING_USER_ID)).thenReturn(false);

        InvalidQuestionnaireException ex = assertThrows(InvalidQuestionnaireException.class,
                () -> questionnaireService.getAllQuestionnaireInfoByOwner(NON_EXISTING_USER_ID));

        assertEquals(InvalidQuestionnaireException.Reason.USER_NOT_FOUND, ex.getReason());
    }

    @Test
    void getAllQuestionnairesInfoByOwnerShouldReturnEmptyListWhenUserHasNoQuestionnaires() {
        when(userService.userExists(questionnaire.getOwner().getId())).thenReturn(true);
        when(questionnaireRepository.findAllByOwnerId(questionnaire.getOwner().getId()))
                .thenReturn(new ArrayList<>());

        List<QuestionnaireInfoDTO> dtos = questionnaireService.getAllQuestionnaireInfoByOwner(
                questionnaire.getOwner().getId());

        assertEquals(0, dtos.size());
    }

    @Test
    void updateQuestionnaireShouldThrowExceptionWhenPassedNonExistingId() {
        when(questionnaireRepository.findById(NON_EXISTING_QUESTIONNAIRE_ID)).thenReturn(Optional.empty());

        InvalidQuestionnaireException ex = assertThrows(InvalidQuestionnaireException.class,
                () -> questionnaireService.updateQuestionnaireInfo(NON_EXISTING_QUESTIONNAIRE_ID, questionnaireUpdateInfoDTO));

        assertEquals(InvalidQuestionnaireException.Reason.NOT_FOUND, ex.getReason());
    }

    @Test
    void updateFieldShouldChangeNotNullFieldsOnly() {
        when(questionnaireRepository.findById(questionnaire.getId())).thenReturn(Optional.of(questionnaire));
        ArgumentCaptor<Questionnaire>  fieldArgumentCaptor = ArgumentCaptor.forClass(Questionnaire.class);

        questionnaireService.updateQuestionnaireInfo(questionnaire.getId(), questionnaireUpdateInfoDTO);
        verify(questionnaireRepository).save(fieldArgumentCaptor.capture());
        Questionnaire questionnaireToSave = fieldArgumentCaptor.getValue();

        // Null fields
        assertEquals(questionnaire.getDescription(), questionnaireToSave.getDescription());

        assertEquals(questionnaire.getResponses(), questionnaireToSave.getResponses());
        assertEquals(questionnaire.getOwner(), questionnaireToSave.getOwner());
        assertEquals(questionnaire.getFields(), questionnaireToSave.getFields());

        // Not null fields
        assertEquals(questionnaire.getName(), questionnaireToSave.getName());
    }

    @Test
    void deleteFieldShouldThrowExceptionWhenPassedNonExistingId() {
        when(questionnaireRepository.deleteQuestionnaireById(NON_EXISTING_QUESTIONNAIRE_ID))
                .thenReturn(0);

        InvalidQuestionnaireException ex = assertThrows(InvalidQuestionnaireException.class,
                () -> questionnaireService.deleteQuestionnaire(NON_EXISTING_QUESTIONNAIRE_ID));

        assertEquals(InvalidQuestionnaireException.Reason.NOT_FOUND, ex.getReason());
    }
    // fixme move to user test
    /*
     */
}