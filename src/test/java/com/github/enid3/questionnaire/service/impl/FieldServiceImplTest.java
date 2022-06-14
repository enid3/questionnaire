package com.github.enid3.questionnaire.service.impl;

import com.github.enid3.questionnaire.data.dto.field.FieldDTO;
import com.github.enid3.questionnaire.data.dto.field.FieldLabelDTO;
import com.github.enid3.questionnaire.data.dto.field.FieldUpdateDTO;
import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.mapper.FieldMapper;
import com.github.enid3.questionnaire.data.repository.FieldsRepository;
import com.github.enid3.questionnaire.service.QuestionnaireService;
import com.github.enid3.questionnaire.service.exception.field.FieldExceptionFactory;
import com.github.enid3.questionnaire.service.exception.field.InvalidFieldException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FieldServiceImplTest {
    @Mock
    private FieldsRepository fieldsRepository;
    private FieldMapper fieldMapper = Mappers.getMapper(FieldMapper.class);

    private FieldExceptionFactory fieldExceptionFactory = new FieldExceptionFactory(
            Mockito.mock(MessageSource.class),
            Locale.ENGLISH);

    @Mock
    private QuestionnaireService questionnaireService;

    private FieldServiceImpl fieldService;

    private static final long QUESTIONNAIRE_ID = 11L;
    private static final FieldDTO fieldDTOToBeCreated = FieldDTO.builder()
            .label("some label")
            .type(Field.Type.CHECKBOX)
            .isRequired(true)
            .isActive(true)
            .options(new HashSet<>(List.of("opt1", "opt2", "opt3")))
            .build();
    private static final Field field = Field.builder()
            .id(111L)
            .label(fieldDTOToBeCreated.getLabel())
            .type(fieldDTOToBeCreated.getType())
            .isRequired(fieldDTOToBeCreated.getIsRequired())
            .isActive(fieldDTOToBeCreated.getIsActive())
            .options(fieldDTOToBeCreated.getOptions())
            .build();

    private static final FieldUpdateDTO fieldUpdateDto = FieldUpdateDTO.builder()
            .label("new label")
            .options(new HashSet<>(List.of("1", "2")))
            .build();

    private static final List<Field> activeFields = List.of(
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

    private static final long NON_EXISTING_FIELD_ID = 1L;
    private static final long NON_EXISTING_QUESTIONNAIRE_ID = 27L;
    private static final long EXISTING_QUESTIONNAIRE_ID = 33L;

    @BeforeEach
    void init() {
        fieldService = new FieldServiceImpl(
                fieldsRepository, fieldMapper,
                fieldExceptionFactory, questionnaireService);
    }


    @Test
    void getAllActiveFieldLabelsByQuestionnaireShouldReturnAllActiveFieldsInResponseDto() {
        when(questionnaireService.questionnaireExists(EXISTING_QUESTIONNAIRE_ID)).thenReturn(true);
        when(fieldsRepository.findAllByQuestionnaireIdAndIsActive(EXISTING_QUESTIONNAIRE_ID, true)).thenReturn(activeFields);

        List<FieldLabelDTO> fieldLabelDTOS = fieldService.getAllActiveFieldLabelsByQuestionnaire(EXISTING_QUESTIONNAIRE_ID);

        Set<FieldLabelDTO> expectedFieldDtoSet = activeFields
                .stream()
                .map(fieldMapper::toFieldLabelDTO)
                .collect(Collectors.toSet());
        assertEquals(expectedFieldDtoSet, new HashSet<>(fieldLabelDTOS));
    }

    @Test
    void getAllFieldsByQuestionnaireShouldThrowExceptionWhenQuestionnaireNotExists() {
        when(questionnaireService.questionnaireExists(NON_EXISTING_QUESTIONNAIRE_ID))
                .thenReturn(false);

        InvalidFieldException ex = assertThrows(InvalidFieldException.class,
                () -> fieldService.getAllFieldsByQuestionnaire(NON_EXISTING_QUESTIONNAIRE_ID, Pageable.unpaged()));

        assertEquals(InvalidFieldException.Reason.QUESTIONNAIRE_NOT_FOUND, ex.getReason());
    }

    @Test
    void getAllActiveFieldLabelsByQuestionnaireShouldThrowExceptionWhenQuestionnaireNotExists() {
        when(questionnaireService.questionnaireExists(NON_EXISTING_QUESTIONNAIRE_ID))
                .thenReturn(false);

        InvalidFieldException ex = assertThrows(InvalidFieldException.class,
                () -> fieldService.getAllActiveFieldLabelsByQuestionnaire(NON_EXISTING_QUESTIONNAIRE_ID));

        assertEquals(InvalidFieldException.Reason.QUESTIONNAIRE_NOT_FOUND, ex.getReason());
    }

    @Test
    void createFieldShouldReturnNewDTOWithId() {
        when(fieldsRepository.save(Mockito.any())).thenReturn(field);

        FieldDTO dto = fieldService.createField(QUESTIONNAIRE_ID, fieldDTOToBeCreated);

        assertNotNull(dto.getId());
    }

    @Test
    void createFieldShouldSetQuestionnaireBeforeSave() {
        ArgumentCaptor<Field> fieldArgumentCaptor = ArgumentCaptor.forClass(Field.class);

        fieldService.createField(QUESTIONNAIRE_ID, fieldDTOToBeCreated);
        verify(fieldsRepository).save(fieldArgumentCaptor.capture());
        Field fieldToSave = fieldArgumentCaptor.getValue();

        assertEquals(QUESTIONNAIRE_ID, fieldToSave.getQuestionnaire().getId());
    }

    @Test
    void updateFieldShouldThrowExceptionWhenPassedNonExistingId() {
        when(fieldsRepository.findById(NON_EXISTING_FIELD_ID)).thenReturn(Optional.empty());

        InvalidFieldException ex = assertThrows(InvalidFieldException.class,
                () -> fieldService.updateField(NON_EXISTING_FIELD_ID, fieldUpdateDto));

        assertEquals(InvalidFieldException.Reason.NOT_FOUND, ex.getReason());
    }

    @Test
    void updateFieldShouldChangeNotNullFieldsOnly() {
        when(fieldsRepository.findById(field.getId())).thenReturn(Optional.of(field));
        ArgumentCaptor<Field>  fieldArgumentCaptor = ArgumentCaptor.forClass(Field.class);

        fieldService.updateField(field.getId(), fieldUpdateDto);
        verify(fieldsRepository).save(fieldArgumentCaptor.capture());
        Field fieldToSave = fieldArgumentCaptor.getValue();

        //Null fields
        assertEquals(field.getType(), fieldToSave.getType());
        assertEquals(field.getIsActive(), fieldToSave.getIsActive());
        assertEquals(field.getIsRequired(), fieldToSave.getIsRequired());

        // Null fields
        assertEquals(field.getLabel(), fieldUpdateDto.getLabel());
        assertEquals(field.getOptions(), fieldUpdateDto.getOptions());
    }

    @Test
    void deleteFieldShouldThrowExceptionWhenPassedNonExistingId() {
        when(fieldsRepository.deleteFieldById(NON_EXISTING_FIELD_ID))
                .thenReturn(0L);

        InvalidFieldException ex = assertThrows(InvalidFieldException.class,
                () -> fieldService.deleteField(NON_EXISTING_FIELD_ID));

        assertEquals(InvalidFieldException.Reason.NOT_FOUND, ex.getReason());
    }
}